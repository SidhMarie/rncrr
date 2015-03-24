package etalas.rncrr.model.process.dsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidh on 23.03.2015.
 */
public class Butterfly {

    public static final double DoublePi = 2*Math.PI;

    public static Complex[] DecimationInTime(Complex[] frame, boolean direct) {
        if(frame.length == 1) return new Complex[] { frame[0] };
        int frameHalfSize = frame.length >> 1;
        int frameFullSize = frame.length;

        Complex[] frameOdd = new Complex[frameHalfSize];
        Complex[] frameEven = new Complex[frameHalfSize];
        for (int i = 0; i < frameHalfSize; i++)
        {
            int j = i << 1; // i = 2*j;
            frameOdd[i] = frame[j + 1];
            frameEven[i] = frame[j];
        }

        Complex[] spectrumOdd = DecimationInTime(frameOdd, direct);
        Complex[] spectrumEven = DecimationInTime(frameEven, direct);

        double arg = direct ? -DoublePi/frameFullSize : DoublePi/frameFullSize;
        Complex omegaPowBase = new Complex(Math.cos(arg), Math.sin(arg));
        Complex omega = new Complex(1,0);
        Complex[] spectrum = new Complex[frameFullSize];

        for (int j = 0; j < frameHalfSize; j++)
        {
            spectrum[j] = spectrumEven[j].plus(omega.times(spectrumOdd[j]));
            spectrum[j + frameHalfSize] = spectrumEven[j].minus(omega.times(spectrumOdd[j]));
            omega = omega.times(omegaPowBase);
        }

        return spectrum;
    }

    public static Complex[] DecimationInFrequency(Complex[] frame, boolean direct)
    {
        if (frame.length == 1) return frame;
        int halfSampleSize = frame.length >> 1; // frame.Length/2
        int fullSampleSize = frame.length;

        double arg = direct ? -DoublePi/fullSampleSize : DoublePi/fullSampleSize;
        Complex omegaPowBase = new Complex(Math.cos(arg), Math.sin(arg));
        Complex omega = new Complex(1,0);
        Complex[] spectrum = new Complex[fullSampleSize];

        for (int j = 0; j < halfSampleSize; j++)
        {
            spectrum[j] = frame[j].plus(frame[j + halfSampleSize]);
            spectrum[j + halfSampleSize] = omega.times(frame[j].minus(frame[j + halfSampleSize]));
            omega = omega.times(omegaPowBase);
        }

        Complex[] yTop = new Complex[halfSampleSize];
        Complex[] yBottom = new Complex[halfSampleSize];
        for (int i = 0; i < halfSampleSize; i++)
        {
            yTop[i] = spectrum[i];
            yBottom[i] = spectrum[i + halfSampleSize];
        }

        yTop = DecimationInFrequency(yTop, direct);
        yBottom = DecimationInFrequency(yBottom, direct);
        for (int i = 0; i < halfSampleSize; i++)
        {
            int j = i << 1; // i = 2*j;
            spectrum[j] = yTop[i];
            spectrum[j + 1] = yBottom[i];
        }

        return spectrum;
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

//        int N = (int) Math.pow(2, 20);;
        int N = list.size();
        Complex[] frame = new Complex[N];
        for (int i = 0; i < N; i++) {
            frame[i] = new Complex(list.get(i), 0);
//            frame[i] = new Complex(-2*Math.random() + 1, 0);
        }
//        show(frame, "frame");
        Complex[] x = DecimationInTime(frame, true);
        show(x, "x");
        Complex[] z = DecimationInTime(x, false);
        show(z, "z");
//        Complex[] z = DecimationInFrequency(frame, true);
//        show(z, "z");
        long timeSpent = System.nanoTime() - startTime;
        System.out.println(timeSpent);
    }

}
