package rncrr.llt.model.dsp;

/**
 * Created by Sidh on 20.04.2016.
 */
public class MathHelper {

    public static double sum(double[] value) {
        double sum = 0D;
        for (double dv : value) {
            sum += dv;
        }
        return sum;
    }

    public static double sumX2(double[] xValue) {
        double sum = 0D;
        for (double dv : xValue) {
            sum += dv*dv;
        }
        return sum;
    }

    public static double sumXY(double[] xValue, double[] yValue) {
        double sum = 0D;
        for (int i = 0; i < xValue.length; i++) {
            sum += xValue[i]*yValue[i];
        }
        return sum;
    }

    public static double mean(double[] value) {
        return sum(value) / value.length;
    }

    public static double mean(double sum, int size){
        return sum/size;
    }

    public static double std(double[] value) {
        double tmp = 0;
        for (double aValue : value) {
            tmp += Math.pow((aValue - mean(value)), 2);
        }
        return Math.pow((tmp/(value.length-1)), 0.5);
    }

    public static double round(double number, int scale) {
        double pow = Math.pow(10,scale);
        double tmp = number * pow;
        return (double) ((int)((tmp - (int) tmp) >= 0.5 ? tmp + 1 : tmp)) / pow;
    }


    public static void main(String[] args) {
        double[] y = new double[]{2.0, 4.0, 2.3, 3.8, 3.4, 1.8, 2.0, 1.3, 2.7, 2.1};
        System.out.println("mean = "+MathHelper.mean(y));
        System.out.println("std = "+MathHelper.std(y));
    }
}
