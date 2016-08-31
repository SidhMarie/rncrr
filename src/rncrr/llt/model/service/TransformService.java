package rncrr.llt.model.service;


import rncrr.llt.model.bean.BeamData;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.bean.eobject.ECharts;
import rncrr.llt.model.bean.eobject.EWindows;
import rncrr.llt.model.dsp.Complex;
import rncrr.llt.model.dsp.OptimalFilter;
import rncrr.llt.model.dsp.Transform;
import rncrr.llt.model.dsp.Window;
import rncrr.llt.model.service.api.ISourceDataService;
import rncrr.llt.model.service.api.ITransformService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidh on 06.04.2015.
 */
public class TransformService implements ITransformService {

    private Complex[] spectrum;
    private double[] noise;
    private DigitalSeries dSeries;
    private ISourceDataService sourceDataService;

    public TransformService() {
        sourceDataService = new SourceDataService();
    }

    @Override
    public DigitalSeries getDigitalSeries(ISourceSeries selectedSeries, ECharts eCharts, Object windowValue) {
        try{
            switch (eCharts) {
                case SOURCE :
                    return getSourceSeries(selectedSeries);
                case SPECTRUM :
                    return getAmplitudeSpectrum(selectedSeries, EWindows.getNameByValue(windowValue.toString()));
                case RECONSTRUCTED:
                    return getReconstructed(selectedSeries, EWindows.getNameByValue(windowValue.toString()));
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

    @Override
    public void setNoise(double[] noise, double limValue) {
        this.noise = new double[spectrum.length];
        for(int i = 0; i<spectrum.length; i++) {
            if(i < noise.length) {
                this.noise[i] = noise[i];
            } else if(i >= noise.length) {
                this.noise[i] = limValue;
            }
            System.out.println(i+" = >> "+this.noise[i]);
        }
    }

    @Override
    public void setNoise(double noise){
        this.noise = new double[spectrum.length];
        for(int i = 0; i < spectrum.length; i++){
            this.noise[i] = noise;
        }
    }

    private void setRebuildData(Complex[] source){
        BeamData reportSeries;
        for(int i = 0; i< sourceDataService.getTransformDataList().size(); i++){
            reportSeries = sourceDataService.getTransformDataList().get(i);
            reportSeries.setRebuild(source[i].re());
        }
    }

    private double getDelta(ISourceSeries sourceSeries) {
        Double point1 = Math.abs(sourceSeries.getXPoints().get(0));
        Double point2 = Math.abs(sourceSeries.getXPoints().get(1));
        return Math.abs(point1 - point2);
    }

    private Double[] getFrequencySeries(ISourceSeries sourceSeries, int N) {
        double f;
        double delta = getDelta(sourceSeries);
        double nyquist = 1/(2*delta);
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
        Double[] frequency = new Double[tf.size()];
        return tf.toArray(frequency);
    }

    private DigitalSeries getSourceSeries(ISourceSeries sSeries){
        dSeries = new DigitalSeries();
        for(Points points : sSeries.getPoints()) {
            dSeries.addPoints(points);
        }
        return dSeries;
    }

    private DigitalSeries getAmplitudeSpectrum(ISourceSeries sSeries, EWindows windows) {
        List<Double> inpList = Transform.inputList(Window.getWindowsData(windows, sSeries.getXPoints()));
        spectrum = getFFTValues(inpList);
        Double[] frequency = getFrequencySeries(sSeries, spectrum.length);
        double[] nSpectrum = new double[frequency.length];
        dSeries = new DigitalSeries();
        for(int i = 0; i < frequency.length; i++) {
            nSpectrum[i] = spectrum[i].abs();
            dSeries.addPoints(new Points(i, nSpectrum[i]));
        }

        sourceDataService.setSourceData(sSeries, inpList, windows, nSpectrum, frequency);
        return dSeries;
    }

    private Complex[] getFFTValues(List<Double> inputList) {
        int n = inputList.size();
        Complex[] result = new Complex[n];
        for (int i = 0; i < n; i++) {
            result[i] = new Complex(inputList.get(i), 0d);
        }
        return Transform.directTransform(result);
    }

    private DigitalSeries getReconstructed(ISourceSeries sSeries) {
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
        setRebuildData(source);
        return dSeries;
    }

    //todo сделать рефакторинг
    private DigitalSeries getReconstructed(ISourceSeries sSeries, EWindows windows) {
        Complex[]  source;
        if(noise != null) { // todo
            OptimalFilter optimalFilter = new OptimalFilter();
            Complex[] filter = optimalFilter.getOptimalFilter(spectrum, noise);
            source = Transform.inverseTransform(filter);
        } else {
            source = Transform.inverseTransform(spectrum);
        }
        dSeries = new DigitalSeries();
        List<Double> yPoint = sSeries.getYPoints();
        int frameSize = yPoint.size();
        double x;
        switch (windows) {
            case RECTANGULAR:
                for(int i = 0; i < frameSize; i++) {
                    x = getX(source[i].re(),yPoint.get(i), Window.rectangular(i, frameSize));
                    if(x != 0D){
                        dSeries.addPoints(new Points(x , yPoint.get(i)));
                    }
                }
                break;
            case GAUSS:
                for(int i = 0; i < frameSize; i++) {
                    x = getX(source[i].re(), yPoint.get(i), Window.gauss(i, frameSize));
                    if(x != 0D){
                        dSeries.addPoints(new Points(x , yPoint.get(i)));
                    }
                }
                break;
            case HAMMING:
                for(int i = 0; i < frameSize; i++) {
                    x = getX(source[i].re(), yPoint.get(i), Window.hamming(i, frameSize));
                    if(x != 0D){
                        dSeries.addPoints(new Points(x , yPoint.get(i)));
                    }
                }
                break;
            case HANN:
                for(int i = 0; i < frameSize; i++) {
                    x = getX(source[i].re(), yPoint.get(i), Window.hann(i, frameSize));
                    if(x != 0D){
                        dSeries.addPoints(new Points(x, yPoint.get(i)));
                    }
                }
                break;
            case BLACKMAN_HARRIS:
                for(int i = 0; i < frameSize; i++) {
                    x = getX(source[i].re(), yPoint.get(i), Window.blackmanHarris(i, frameSize));
                    if(x != 0D){
                        dSeries.addPoints(new Points(x , yPoint.get(i)));
                    }
                }
                break;
            default:
                for(int i = 0; i < frameSize; i++) {
                    x = getX(source[i].re(), yPoint.get(i), Window.rectangular(i, frameSize));
                    if(x != 0D){
                        dSeries.addPoints(new Points(x , yPoint.get(i)));
                    }
                }
        }
        setRebuildData(source);//todo данные без учета деления на окно
        noise = null;
        return dSeries;
    }

    private double getX(double s, double b, double windowValue){
        double x = s == 0 ? b : s / windowValue;
        if(x != Double.POSITIVE_INFINITY && x != Double.NEGATIVE_INFINITY) {
            return x;
        }
        return b;
    }

    private double getX(double s, double b){
        double x = s == 0 ? b : s;
        if(x != Double.POSITIVE_INFINITY && x != Double.NEGATIVE_INFINITY) {
            return x;
        }
        return b;
    }


    private DigitalSeries getDWindows(ISourceSeries sSeries, EWindows windows) {
        List<Double> wList = Window.getWindowsData(windows, sSeries.getXPoints());
        int n = wList.size();
        List<Double> yList = sSeries.getYPoints();
        dSeries = new DigitalSeries();
        for(int i = 0; i < n; i++){
            dSeries.addPoints(new Points(wList.get(i), yList.get(i)));
        }
        return dSeries;
    }


}
