package etalas.rncrr.model.process;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidh on 05.03.2015.
 */
public class FFT {

    // Прореживание по времени
    public static Complex[] fft(Complex[] frame) {
        if (frame.length == 1) return new Complex[] { frame[0] };
        int halfSize = frame.length >> 1;
        int frameSize = frame.length;

        if (frameSize % 2 != 0) { throw new RuntimeException("N is not a power of 2"); }

        Complex[] even = new Complex[halfSize];
        for (int k = 0; k < halfSize; k++) {
            even[k] = frame[2*k];
        }
        Complex[] q = fft(even);

        for (int k = 0; k < halfSize; k++)
            even[k] = frame[2 * k + 1];
        Complex[] r = fft(even);

        // combine
        Complex[] y = new Complex[frameSize];
        for (int k = 0; k < halfSize; k++) {
            double kth = -2 * k * Math.PI / frameSize;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k] = q[k].plus(wk.times(r[k]));
            y[k + halfSize] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }


    // compute the inverse FFT of x[], assuming its length is a power of 2
    public static Complex[] ifft(Complex[] x) {
        int N = x.length;
        Complex[] y = new Complex[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward FFT
        y = fft(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].times(1.0 / N);
        }

        return y;

    }

    // compute the circular convolution of x and y
    public static Complex[] cconvolve(Complex[] x, Complex[] y) {

        // should probably pad x and y with 0s so that they have same length
        // and are powers of 2
        if (x.length != y.length) { throw new RuntimeException("Dimensions don't agree"); }

        int N = x.length;

        // compute FFT of each sequence
        Complex[] a = fft(x);
        Complex[] b = fft(y);

        // point-wise multiply
        Complex[] c = new Complex[N];
        for (int i = 0; i < N; i++) {
            c[i] = a[i].times(b[i]);
        }

        // compute inverse FFT
        return ifft(c);
    }


    // compute the linear convolution of x and y
    public static Complex[] convolve(Complex[] x, Complex[] y) {
        Complex ZERO = new Complex(0, 0);

        Complex[] a = new Complex[2*x.length];
        System.arraycopy(x, 0, a, 0, x.length);
        for (int i = x.length; i < 2*x.length; i++) a[i] = ZERO;

        Complex[] b = new Complex[2*y.length];
        System.arraycopy(y, 0, b, 0, y.length);
        for (int i = y.length; i < 2*y.length; i++) b[i] = ZERO;

        return cconvolve(a, b);
    }

    // display an array of Complex numbers to standard output
    public static void show(Complex[] x, String title) {
        System.out.println(title);
        System.out.println("-------------------");
        for (Complex aX : x) {
            System.out.println(aX);
        }
        System.out.println();
    }


    /*********************************************************************
     *  Test client and sample execution
     *
     *  % java FFT 4
     *  x
     *  -------------------
     *  -0.03480425839330703
     *  0.07910192950176387
     *  0.7233322451735928
     *  0.1659819820667019
     *
     *  y = fft(x)
     *  -------------------
     *  0.9336118983487516
     *  -0.7581365035668999 + 0.08688005256493803i
     *  0.44344407521182005
     *  -0.7581365035668999 - 0.08688005256493803i
     *
     *  z = ifft(y)
     *  -------------------
     *  -0.03480425839330703
     *  0.07910192950176387 + 2.6599344570851287E-18i
     *  0.7233322451735928
     *  0.1659819820667019 - 2.6599344570851287E-18i
     *
     *  c = cconvolve(x, x)
     *  -------------------
     *  0.5506798633981853
     *  0.23461407150576394 - 4.033186818023279E-18i
     *  -0.016542951108772352
     *  0.10288019294318276 + 4.033186818023279E-18i
     *
     *  d = convolve(x, x)
     *  -------------------
     *  0.001211336402308083 - 3.122502256758253E-17i
     *  -0.005506167987577068 - 5.058885073636224E-17i
     *  -0.044092969479563274 + 2.1934338938072244E-18i
     *  0.10288019294318276 - 3.6147323062478115E-17i
     *  0.5494685269958772 + 3.122502256758253E-17i
     *  0.240120239493341 + 4.655566391833896E-17i
     *  0.02755001837079092 - 2.1934338938072244E-18i
     *  4.01805098805014E-17i
     *
     *********************************************************************/

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
//        int N = list.size();
        int N = 32768;

        Complex[] x = new Complex[N];

        // original data
        for (int i = 0; i < N; i++) {
//            x[i] = new Complex(i, 0);
//            x[i] = new Complex(list.get(i), 0);
            x[i] = new Complex(-2*Math.random() + 1, 0);

        }
//        show(x, "x");

        // FFT of original data
        Complex[] y = fft(x);
//        show(y, "y = fft(x)");
        long timeSpent = System.nanoTime() - startTime;
        System.out.println(timeSpent);
//
//        // take inverse FFT
//        Complex[] z = ifft(y);
//        show(z, "z = ifft(y)");
//
//        // circular convolution of x with itself
//        Complex[] c = cconvolve(x, x);
//        show(c, "c = cconvolve(x, x)");
//
//        // linear convolution of x with itself
//        Complex[] d = convolve(x, x);
//        show(d, "d = convolve(x, x)");
    }

}
