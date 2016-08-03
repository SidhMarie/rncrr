package rncrr.llt.model.dsp;

import rncrr.llt.model.bean.eobject.EWindows;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidh on 24.03.2015.
 */
public class Window {

    /**
     * Прямоугольное окно
     */
    public static double rectangular(int n, int size){
        return 1D;
    }

    /**
     * Окно Гауса
     */
    public static double gauss(int n, int size) {
        double a = (size - 1)/2;
        double b = (n - a)/(0.5*a);
        return Math.exp(-(b*b) / 2);
    }

    /**
     * Окно Хемминга
     */
    public static double hamming(int n, int size) {
        return 0.54 - 0.46*Math.cos((2*Math.PI * n) / (size - 1));
    }

    /**
     * Окно Ханна
     */
    public static double hann(int n, int size) {
        return 0.5*(1 - Math.cos((2*Math.PI * n) / (size - 1)));
    }

    /**
     * Окно Блэкмана — Харриса
     */
    public static double blackmanHarris(int n, int size) {
        return 0.35875 - (0.48829*Math.cos((2*Math.PI * n) / (size - 1))) +
                (0.14128*Math.cos((4*Math.PI * n) / (size - 1))) - (0.01168*Math.cos((6*Math.PI * n) / (size - 1)));
    }

    public static List<Double> getWindowsData(EWindows windows, List<Double> list) {
        List<Double> result = new ArrayList<>();
        switch (windows) {
            case RECTANGULAR :
                for(int i = 0; i<list.size(); i++){
                    result.add(list.get(i) * Window.rectangular(i, list.size()));
                }
                break;
            case GAUSS :
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.gauss(i, list.size()));
                }
                break;
            case HAMMING :
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.hamming(i, list.size()));
                }
                break;
            case HANN :
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.hann(i, list.size()));
                }
                break;
            case BLACKMAN_HARRIS :
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.blackmanHarris(i, list.size()));
                }
                break;
            default:
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.rectangular(i, list.size()));
                }
        }
        return result;
    }

}
