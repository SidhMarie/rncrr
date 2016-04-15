package rncrr.llt.model.service;


import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.TransformDataSeries;
import rncrr.llt.model.process.ExcelBuilder;
import rncrr.llt.model.process.api.ISourceSeries;
import rncrr.llt.model.process.dsp.Complex;
import rncrr.llt.model.process.dsp.Transform;
import rncrr.llt.model.process.dsp.Wiener;
import rncrr.llt.model.process.dsp.Window;
import rncrr.llt.model.service.api.ITransformService;
import rncrr.llt.model.utils.Config;
import rncrr.llt.model.utils.eobject.ECharts;
import rncrr.llt.model.utils.eobject.EWindows;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sidh on 06.04.2015.
 */
public class TransformService implements ITransformService {

    private Complex[] spectrum;
    private Complex[] filter;
    private DigitalSeries dSeries;

    private Double delta;
    private Double nyquist;
    private Double[] frequency;

    private static Map<Object,List<TransformDataSeries>> transformData;
    private List<TransformDataSeries> transformList;


    public TransformService() {
        transformData = new HashMap<>();
    }

    @Override
    public DigitalSeries getDigitalSeries(ISourceSeries selectedSeries, ECharts eCharts, Object windowValue) {
        try{
            switch (eCharts) {
                case SOURCE :
                    return getSourceSeries(selectedSeries);
                case SPECTRUM :
                    return getAmplitudeSpectrum(selectedSeries, windows(windowValue));
//                case WIENNER:
//                    return getWiennerFilter();
//                    return getDWindows(selectedSeries, windows(windowValue));
                case RECONSTRUCTED:
                    return getReconstructed(selectedSeries, windows(windowValue));
//                    return getReconstructed(selectedSeries);
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            dSeries = null;
        }
        return null;
    }

    public static void printData() {
        ExcelBuilder eb = new ExcelBuilder(new File(Config.getStringProperty("export.xls.file.name")));
        for(Object key : transformData.keySet()) {
            List<TransformDataSeries> list = transformData.get(key);
            eb.createSheet(key.toString(), list);
        }
    }

    private void setSourceData(ISourceSeries series, List<Double> inputList, EWindows windows, double[] nSpectrum, Complex[] filter) {
        transformList = new ArrayList<>();
        TransformDataSeries reportSeries;
        for(int i = 0; i <inputList.size(); i++){
            reportSeries = new TransformDataSeries();
            reportSeries.setCount(i + 1);
            if(i < series.getPoints().size()) {
                reportSeries.setSourceX(series.getXPoints().get(i));
                reportSeries.setSourceY(series.getYPoints().get(i));
            } else {
                reportSeries.setSourceX(0D);
                reportSeries.setSourceY(0D);
            }
            reportSeries.setWindowX(inputList.get(i));
            if(i < frequency.length) {
                reportSeries.setFrequency(frequency[i]);
            } else {
                reportSeries.setFrequency(0D);
            }
            if(i < nSpectrum.length){
                reportSeries.setAmplitude(nSpectrum[i]);
            } else {
                reportSeries.setAmplitude(0D);
            }
            transformList.add(reportSeries);
        }
        transformData.put(windows.name(), transformList);
    }

    private void setRebuildData(Complex[] source){
        TransformDataSeries reportSeries;
        for(int i = 0; i< transformList.size(); i++){
            reportSeries = transformList.get(i);
            reportSeries.setRebuild(source[i].re());
        }
    }

    private void doDelta(ISourceSeries sourceSeries) {
        Double point1 = Math.abs(sourceSeries.getXPoints().get(0));
        Double point2 = Math.abs(sourceSeries.getXPoints().get(1));
        delta = Math.abs(point1 - point2);
    }

    private void doNyquist(){
        nyquist = 1/(2*delta);
    }

    private void doFrequency(int N) {
        double f;
        List<Double> tf = new ArrayList<>();
        for(int i = 0; i < N; i++){
            if(i == 0 ) {
                tf.add(0D);
            } else {
                f = (i/(N*delta));
                if(f < nyquist)
                    tf.add(f);
            }
        }
        frequency = new Double[tf.size()];
        tf.toArray(frequency);
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
        List<Double> winList = setWindowsData(windows, sSeries.getXPoints());
        List<Double> inpList = Transform.inputList(winList);
        doDelta(sSeries);
        doNyquist();
        spectrum = doFFTValues(inpList);
        doFrequency(spectrum.length);
        double[] nSpectrum = new double[frequency.length];
        dSeries = new DigitalSeries();
        for(int i = 0; i < frequency.length; i++) {
            nSpectrum[i] = spectrum[i].abs();
            dSeries.addPoints(new Points(i, nSpectrum[i]));
        }
        Wiener wiener = new Wiener(spectrum, frequency);
        filter = wiener.getWiennerFilter(10, nSpectrum);
        setSourceData(sSeries, inpList, windows, nSpectrum, filter);
        return dSeries;
    }

    private Complex[] doFFTValues(List<Double> inputList) {
        int n = inputList.size();
        Complex[] result = new Complex[n];
        for (int i = 0; i < n; i++) {
            result[i] = new Complex(inputList.get(i), 0d);
        }
        return Transform.directTransform(result);
    }


    private DigitalSeries getReconstructed(ISourceSeries sSeries) {
//        Complex[] source = Transform.inverseTransform(spectrum);
//        System.out.println("spectrum =====>> "+spectrum.length);
//        for(int i = 0; i <spectrum.length; i++){
//            System.out.println("("+filter[i].re()+"   "+filter[i].im()+")              ("+spectrum[i].re()+"   "+spectrum[i].im()+")");
//        }
        Complex[] source = Transform.inverseTransform(filter);
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
        setRebuildData(source);
        return dSeries;
    }

    private DigitalSeries getReconstructed(ISourceSeries sSeries, EWindows windows) {
//        Complex[] source = Transform.inverseTransform(spectrum);
        Complex[]  source = Transform.inverseTransform(filter);
        dSeries = new DigitalSeries();
        List<Double> yPoint = sSeries.getYPoints();
        int frameSize = yPoint.size();
        double x;
        switch (windows) {
            case RECTANGULAR:
                System.out.println(" frameSize => "+frameSize);
                for(int i = 0; i < frameSize; i++) {
                    dSeries.addPoints(new Points(source[i].re(), yPoint.get(i)));
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
