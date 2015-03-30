package etalas.rncrr.model.process.dsp;

/**
 * Created by Sidh on 24.03.2015.
 */
public class Window {

    /**
     * Прямоугольное окно
     */
    public static double rectangular(double n, double size){
        return 1.0;
    }

    /**
     * Окно Гауса
     */
    public static double gauss(double n, double size) {
        double a = (size - 1)/2;
        double b = (n - a)/(0.5*a);
        return Math.exp(-(b*b) / 2);
    }

    /**
     * Окно Гауса
     */
    public static double gauss(double n, double size, double q) {
        double a = (size - 1)/2;
        double b = (n - a)/(q*a);
        return Math.exp(-(b*b) / 2);
    }

    /**
     * Окно Хемминга
     */
    public static double hamming(double n, double size) {
        return 0.54 - 0.46*Math.cos((2*Math.PI * n) / (size - 1));
    }

    /**
     * Окно Ханна
     */
    public static double hann(double n, double size) {
        return 0.5*(1 - Math.cos((2*Math.PI * n) / (size - 1)));
    }

    /**
     * Окно Блэкмана — Харриса
     */
    public static double blackmanHarris(double n, double size) {
        return 0.35875 - (0.48829*Math.cos((2*Math.PI * n) / (size - 1))) +
                (0.14128*Math.cos((4*Math.PI * n) / (size - 1))) - (0.01168*Math.cos((6*Math.PI * n) / (size - 1)));
    }

}
