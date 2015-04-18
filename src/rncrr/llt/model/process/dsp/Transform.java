package rncrr.llt.model.process.dsp;


import java.util.List;

import static java.lang.Math.*;

/**
 * Created by Sidh on 24.03.2015.
 */
public class Transform {

    private static final double DoublePi = 2*PI;

    public static int[] rate2 = {2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,
            65536,131072,262144,524288,1048576,2097152,4194304,8388608,16777216,
            33554432,67108864,134217728,268435456,536870912,1073741824};

    public static List<Double> inputList(List<Double> list){
        int size = list.size();
        for (int d2 : rate2) {
            if (size < d2) {
                while (size < d2) {
                    list.add(0.0);
                    size++;
                }
                return list;
            }
        }
        return list;
    }

    /**
     * direct Fourier transform with decimation-in-time
     * @param frame
     * @return
     */
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
            wk = new Complex(cos(-k * DoublePi / frameSize), sin(-k * DoublePi / frameSize));
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
