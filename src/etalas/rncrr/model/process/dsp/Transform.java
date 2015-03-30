package etalas.rncrr.model.process.dsp;


/**
 * Created by Sidh on 24.03.2015.
 */
public class Transform {

    private static final double DoublePi = 2*Math.PI;

    // прорежиание по времени
    public static Complex[] directTransform(Complex[] frame) {
        if(frame.length == 1) return new Complex[] { frame[0] };
        int halfSize = frame.length >> 1;
        int frameSize = frame.length;

        if (frameSize % 2 != 0) { throw new RuntimeException("N is not a power of 2"); }

        Complex[] even = new Complex[halfSize];
        for (int k = 0; k < halfSize; k++)
            even[k] = frame[2*k];
        Complex[] alpha = directTransform(even);

        for (int k = 0; k < halfSize; k++)
            even[k] = frame[2 * k + 1];
        Complex[] omega = directTransform(even);

        // combine
        Complex[] spectrum = new Complex[frameSize];
        Complex wk;
        for (int k = 0; k < halfSize; k++) {
            wk = new Complex(Math.cos(-k*DoublePi / frameSize), Math.sin(-k*DoublePi / frameSize));
            spectrum[k] = alpha[k].plus(wk.times(omega[k]));
            spectrum[k + halfSize] = alpha[k].minus(wk.times(omega[k]));
        }
        return spectrum;
    }

    public static Complex[] inverseTransform(Complex[] frame) {
        if(frame.length == 1) return new Complex[] { frame[0] };
        int frameFullSize = frame.length;

        Complex[] source = new Complex[frameFullSize];
        for (int i = 0; i < frameFullSize; i++) {
            source[i] = frame[i].conjugate();
        }

        source = directTransform(source);

        for (int i = 0; i < frameFullSize; i++) {
            source[i] = source[i].conjugate();
            source[i] = source[i].times(1.0 / frameFullSize);
        }
        return source;
    }

}
