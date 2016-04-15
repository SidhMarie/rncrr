package rncrr.llt.model.process.dsp;

import rncrr.llt.model.bean.DigitalSeries;

/**
 * Created by Sidh on 22.03.2016.
 */
public class Wiener {

    private Complex[] spectrum;
    private Double[] frequency;
    private Complex[] filter;

    public Wiener(Complex[] spectrum, Double[] frequency) {
        this.spectrum = spectrum;
        this.frequency = frequency;
    }

    public Complex[] getWiennerFilter(int sizeField, double[] nSpectrum) {
        filter = new Complex[spectrum.length];
        double[] wiener = new double[spectrum.length];
        double noise = getNoise(frequency.length, sizeField, nSpectrum);
        for(int i = 0; i < spectrum.length; i++) {
            wiener[i] = spectrum[i].abs()/(spectrum[i].abs()+noise);
            filter[i] = new Complex(spectrum[i].re()*wiener[i], spectrum[i].im()*wiener[i]);
        }
        return filter;
    }

    private double getNoise(int sizeFreq, int sizeField, double[] nSpectrum) {
        double sum = 0D;
        for(int i = sizeFreq-1; i > sizeFreq - 1 - sizeField; i--){
            sum += nSpectrum[i];
        }
        return sum/sizeField;
    }

}
