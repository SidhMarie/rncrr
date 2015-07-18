package rncrr.llt.model.process.dsp;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * Created by Sidh on 24.03.2015.
 */
public class Transform {

    private static final double DoublePi = 2*PI;

    public static List<Double> inputList(List<Double> list){
        int size = list.size();
        int rate = bit2(size);
        while (size < rate) {
            list.add(0D);
            size++;
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

    /**
     * calculate the closest of degree 2
     * @param x - input value
     * @return closest of degree 2 for x
     */
    private static int bit2(int x) {
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        return x + 1;
    }

    public static void main(String[] args) {
        List<Double> list = new ArrayList<>();
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
        list.add(1D);
//        list.add(1D);
//        list.add(1D);
//        list.add(1D);
//        list.add(1D);

        Transform.inputList(list);
        System.out.println(list.size());
        for(double d : list){
            System.out.println(d);
        }
    }

}
