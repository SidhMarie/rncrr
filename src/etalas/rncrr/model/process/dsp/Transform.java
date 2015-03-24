package etalas.rncrr.model.process.dsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidh on 24.03.2015.
 */
public class Transform {

    public static final double DoublePi = 2*Math.PI;

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

    public static void show(Complex[] x, String title) {
        System.out.println(title);
        System.out.println("-------------------");
        for (Complex aX : x) {
            System.out.println(aX);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<Double> list = new ArrayList<>();
        list.add(1.02);
        list.add(2.53);
        list.add(3.69);
        list.add(4.41);
        list.add(5.22);
        list.add(4.50);
        list.add(7.53);
        list.add(1.90);

        long startTime = System.nanoTime();
//        long startTime = System.currentTimeMillis();

        int N = list.size();
//        int N = 4096;
        Complex[] frame = new Complex[N];
        for (int i = 0; i < N; i++) {
            frame[i] = new Complex(list.get(i), 0);
//            frame[i] = new Complex(-2*Math.random() + 1, 0);
        }
//        show(frame, "frame");
        Complex[] x = directTransform(frame);
//        show(x, "x");

        Complex[] y = inverseTransform(x);
//        show(y, "y");

        long timeSpent = System.nanoTime() - startTime;
//        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println(timeSpent);
    }
}
