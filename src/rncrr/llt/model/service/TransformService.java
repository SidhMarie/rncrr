package rncrr.llt.model.service;


import javafx.scene.control.ChoiceBox;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.AscSourceSeries;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.process.dsp.Complex;
import rncrr.llt.model.process.dsp.Transform;
import rncrr.llt.model.process.dsp.Window;
import rncrr.llt.model.service.api.ITransformService;
import rncrr.llt.model.utils.eobject.ECharts;
import rncrr.llt.model.utils.eobject.EWindows;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidh on 06.04.2015.
 */
public class TransformService implements ITransformService {

    private Complex[] signal;
    private Complex[] spectrum;
    private Complex[] filter;

    private DigitalSeries dSeries;

    public TransformService() {}

    @Override
    public void valuesXY(ISourceSeries sSeries, EWindows windows) {
        List<Double> wList = setWindowsData(windows, sSeries.getXPoints());
        List<Double> xList = Transform.inputList(wList);
        int n = xList.size();
        signal = new Complex[n];
        for (int i = 0; i < n; i++) {
            signal[i] = new Complex(xList.get(i), 0d);
        }
        spectrum = Transform.directTransform(signal);
    }

    @Override
    public DigitalSeries getDigitalSeries(ISourceSeries selectedSeries, ECharts eCharts, Object windowValue) {
        try{
            switch (eCharts) {
                case SOURCE :
                    return getSourceSeries(selectedSeries);
                case SPECTRUM :
                    return getAmplitudeSpectrum(selectedSeries, windows(windowValue));
                case WINDOW :
                    return getDWindows(selectedSeries, windows(windowValue));
                case RECONSTRUCTED:
                    return getReconstructed(selectedSeries, windows(windowValue));
                default:
                    return null;
            }
        } catch (Exception e) {
            dSeries = null;
        }
        return null;
    }

    private EWindows windows(Object windowValue){
        return EWindows.getNameByValue(windowValue.toString());
    }

    private DigitalSeries getSourceSeries(ISourceSeries sSeries){
        dSeries = new DigitalSeries();
        for(Points points : sSeries.getPoints()) {
            dSeries.addPoints(points);
        }
        return dSeries;
    }

    private DigitalSeries getAmplitudeSpectrum(ISourceSeries sSeries, EWindows windows) {
        valuesXY(sSeries, windows);
        int n = signal.length/2;
        double[] nSpectrum = new double[n];
        dSeries = new DigitalSeries();
//        System.out.println("spectrum***********************************************");
        for(int i = 0; i < n; i++) {
            nSpectrum[i] = spectrum[i].abs() / n;
//            System.out.println(nSpectrum[i]);
            dSeries.addPoints(new Points(i, nSpectrum[i]));
        }
//        getWiennerFilter();
        return dSeries;
    }

    private void getWiennerFilter() {
//        dSeries = new DigitalSeries();
        int n = signal.length/2;
        System.out.println("n =====>> "+n);
        int v = 5;
        System.out.println("  =====>> " + v );
        double tmp = 0D;
        for(int i = n-1; i > n - 1 - v; i--){
            System.out.println(" ==================>> " + spectrum[i].re());
            tmp += spectrum[i].abs();
        }
        System.out.println(" tmp =====>> "+tmp);
        double noise = tmp/v;
        System.out.println(" avr =====>> "+ noise);
        filter = new Complex[spectrum.length];
        for(int i = 0; i < spectrum.length; i++) {
            double s = spectrum[i].abs() - noise;
            double w = (s*s)/((spectrum[i].abs()* spectrum[i].abs())+(noise*noise));
//            double w = 0.0001;
            filter[i] = new Complex(spectrum[i].re()/(1+w), spectrum[i].im());
        }
    }

    private DigitalSeries getReconstructed2(AscSourceSeries sSeries, EWindows windows) {
        Complex[] source = Transform.inverseTransform(spectrum);
        dSeries = new DigitalSeries();
        List<Double> yPoint = sSeries.getYPoints();
        int ySize = yPoint.size();
        for(int i = 0; i < source.length; i++) {
            if(i < ySize) {
                if(source[i].re() == 0D) {
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    System.out.println("++++++++++++++++++++++ "+source[i].re()+" ++++++ "+yPoint.get(i)+" +++++++++++++++++++");
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                }
                dSeries.addPoints(new Points(source[i].re(), yPoint.get(i)));
            }
        }
        return dSeries;
    }

    private DigitalSeries getReconstructed(ISourceSeries sSeries, EWindows windows) {
        Complex[] source;
        source = Transform.inverseTransform(spectrum);
//        source = Transform.inverseTransform(filter);
        dSeries = new DigitalSeries();
        List<Double> yPoint = sSeries.getYPoints();
        int frameSize = yPoint.size();
        double x;
        switch (windows) {
            case RECTANGULAR:
                System.out.println(" frameSize => "+frameSize);
                for(int i = 0; i < frameSize; i++) {
//                    x = source[i].re() == 0D ? 0D : source[i].abs() / Window.rectangular(i, frameSize);
//                    if(x != 0D){
                        dSeries.addPoints(new Points(source[i].re(), yPoint.get(i)));
//                    }
                }
                break;
            case GAUSS:
                for(int i = 0; i < frameSize; i++) {
                    x = source[i].re() == 0D ? 0D : source[i].re() / Window.gauss(i, frameSize);
                    if(x != 0D){
                        dSeries.addPoints(new Points(x , yPoint.get(i)));
                    }
                }
                break;
            case HAMMING:
                for(int i = 0; i < frameSize; i++) {
                    x = source[i].re() == 0D ? 0D : source[i].re() / Window.hamming(i, frameSize);
                    if(x != 0D){
                        dSeries.addPoints(new Points(x , yPoint.get(i)));
                    }
                }
                break;
            case HANN:
                System.out.println(" frameSize => "+frameSize);
                for(int i = 0; i < frameSize; i++) {
                    x = source[i].re() == 0D ? 0D : source[i].re() / Window.hann(i, frameSize);
                    if(x != 0D){
                        if(x != Double.POSITIVE_INFINITY && x != Double.NEGATIVE_INFINITY) {
                            System.out.println(x);
                            dSeries.addPoints(new Points(x, yPoint.get(i)));
                        }
                    }
                }
                for(int i = 0; i < frameSize; i++) {
                    x = source[i].re() == 0D ? 0D : source[i].re() / Window.hann(i, frameSize);
                    if(x != 0D){
                        if(x != Double.POSITIVE_INFINITY && x != Double.NEGATIVE_INFINITY) {
                            System.out.println(yPoint.get(i));
                            dSeries.addPoints(new Points(x, yPoint.get(i)));
                        }
                    }
                }
                break;
            case BLACKMAN_HARRIS:
                for(int i = 0; i < frameSize; i++) {
                    x = source[i].re() == 0D ? 0D : source[i].re() / Window.blackmanHarris(i, frameSize);
                    if(x != 0D){
                        dSeries.addPoints(new Points(x , yPoint.get(i)));
                    }
                }
                break;
            default:
                for(int i = 0; i < frameSize; i++) {
                    x = source[i].re() == 0D ? 0D : source[i].re() / Window.rectangular(i, frameSize);
                    if(x != 0D){
                        dSeries.addPoints(new Points(x , yPoint.get(i)));
                    }
                }
        }
        return dSeries;
    }

    private DigitalSeries getDWindows(ISourceSeries sSeries, EWindows windows) {
        List<Double> wList = setWindowsData(windows, sSeries.getXPoints());
        int n = wList.size();
        List<Double> yList = sSeries.getYPoints();
        dSeries = new DigitalSeries();
        for(int i = 0; i < n; i++){
            dSeries.addPoints(new Points(wList.get(i), yList.get(i)));
        }
        return dSeries;
    }

    public static List<Double> setWindowsData(EWindows windows, List<Double> list) {
        List<Double> result = new ArrayList<>();
        switch (windows) {
            case RECTANGULAR :
                for(int i = 0; i<list.size(); i++){
                    result.add(list.get(i) * Window.rectangular(i, list.size()));
                }
                break;
            case GAUSS :
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.gauss(i, list.size()));
                }
                break;
            case HAMMING :
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.hamming(i, list.size()));
                }
                break;
            case HANN :
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.hann(i, list.size()));
                }
                break;
            case BLACKMAN_HARRIS :
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.blackmanHarris(i, list.size()));
                }
                break;
            default:
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.rectangular(i, list.size()));
                }
        }
        return result;
    }
}
