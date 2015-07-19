package rncrr.llt.model.process.dsp;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * Created by Sidh on 24.03.2015.
 */
public class Transform {

    private static final double DoublePi = 2*PI;

    /**
     * method create list values with size of degree 2
     * @param list - input list values
     * @return list values with size of degree 2
     */
    public static List<Double> inputList(List<Double> list){
        int size = list.size();
        int rate = bit2(size);
        while (size < rate) {
            list.add(0d);
            size++;
        }
        return list;
    }

    /**
     * direct Fourier transform with decimation-in-time
     * @param frame - input complex series value
     * @return spectrum values
     */
    public static Complex[] directTransform(Complex[] frame) {
        if(frame.length == 1) return new Complex[] { frame[0] };
        int halfSize = frame.length >> 1;
        int frameSize = frame.length;

        if (frameSize % 2 != 0) {
            throw new RuntimeException("N is not a power of 2");
        }

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

    /**
     * inverse Fourier transform
     * @param frame - input complex series value
     * @return source values
     */
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

    // compute the circular convolution of x and y

    /**
     * compute the circular convolution of x and y
     * @param x - input value x
     * @param y - input value y
     * @return
     */
    public static Complex[] circularConvolve(Complex[] x, Complex[] y) {
        if (x.length != y.length) { throw new RuntimeException("Dimensions don't agree"); }
        int N = x.length;
        // compute FFT of each sequence
        Complex[] a = directTransform(x);
        Complex[] b = directTransform(y);
        // point-wise multiply
        Complex[] c = new Complex[N];
        for (int i = 0; i < N; i++) {
            c[i] = a[i].times(b[i]);
        }
        // compute inverse FFT
        return inverseTransform(c);
    }

    /**
     * compute the linear convolution of x and y
     * @param x - input value x
     * @param y - input value y
     * @return -
     */
    public static Complex[] linearConvolve(Complex[] x, Complex[] y) {
        Complex ZERO = new Complex(0, 0);
        Complex[] a = new Complex[2*x.length];
        System.arraycopy(x, 0, a, 0, x.length);
        for (int i = x.length; i < 2*x.length; i++) a[i] = ZERO;

        Complex[] b = new Complex[2*y.length];
        System.arraycopy(y, 0, b, 0, y.length);
        for (int i = y.length; i < 2*y.length; i++) b[i] = ZERO;

        return circularConvolve(a, b);
    }

    /**
     * calculate the closest of degree 2
     * @param val - input value
     * @return closest of degree 2 for val
     */
    private static int bit2(int val) {
        val |= val >> 1;
        val |= val >> 2;
        val |= val >> 4;
        val |= val >> 8;
        val |= val >> 16;
        return val + 1;
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
