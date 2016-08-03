package rncrr.llt.model.dsp;

/**
 * Created by Sidh on 22.03.2016.
 */
public class OptimalFilter {

    private Complex[] spectrum;
    private double[] noise;

    public OptimalFilter(){}

    public OptimalFilter(Complex[] spectrum, double[] noise) {
        this.spectrum = spectrum;
        this.noise = noise;
    }

    public Complex[] getOptimalFilter() {
        Complex[] filter = new Complex[spectrum.length];
        double[] wiener = new double[spectrum.length];
        for(int i = 0; i < spectrum.length; i++) {
            wiener[i] = spectrum[i].abs()/(spectrum[i].abs()+noise[i]);
            filter[i] = new Complex(spectrum[i].re()*wiener[i], spectrum[i].im()*wiener[i]);
        }
        return filter;
    }

    public Complex[] getOptimalFilter(Complex[] spectrum, double[] noise){
        Complex[] filter = new Complex[spectrum.length];
        double[] wiener = new double[spectrum.length];
        for(int i = 0; i < spectrum.length; i++) {
            wiener[i] = spectrum[i].abs()/(spectrum[i].abs()+noise[i]);
            filter[i] = new Complex(spectrum[i].re()*wiener[i], spectrum[i].im()*wiener[i]);
        }
        return filter;
    }

}
