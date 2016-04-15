package rncrr.llt.model.process.dsp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sidh on 07.04.2016.
 * y = f(x) = kx+b
 * k*Sum(x^2) + b*Sum(x) = Sum(x*y)
 * k*Sum(x) + bn = Sum(y)
 */
public class LeastSquares {

    private double delta;
    private double[] matrix;
    private double[] xValue;
    private double[] allXValue;
    private double[] approximate;

    public LeastSquares(double[] allXValue, double[] xValue, double[] yValue) {
        this.allXValue = allXValue;
        this.xValue = xValue;
        setMatrix(xValue, yValue);
        setDelta(matrix);
    }

    public double[] doLeastSquaresApproximation(){
        approximate = new double[xValue.length];
        for (int i = 0; i<xValue.length; i++) {
            approximate[i] = getDeltaK()*xValue[i] + getDeltaB();;
        }
        return approximate;
    }

    public double[] doLeastSquaresExtrapolation(){
        approximate = new double[allXValue.length];
        for (int i = 0; i<allXValue.length; i++) {
            approximate[i] = getDeltaK()*allXValue[i] + getDeltaB();;
        }
        return approximate;
    }

    private double getSumValue(double[] value) {
        double sum = 0D;
        for (double dv : value) {
            sum += dv;
        }
        return sum;
    }

    private double getSumX2(double[] xValue){
        double sum = 0D;
        for (double dv : xValue) {
            sum += dv*dv;
        }
        return sum;
    }

    private double getSumXY(double[] xValue, double[] yValue) {
        double sum = 0D;
        for (int i = 0; i < xValue.length; i++) {
            sum += xValue[i]*yValue[i];
        }
        return sum;
    }

    private void setMatrix(double[] xValue, double[] yValue) {
        double x2 = getSumX2(xValue);
        double x = getSumValue(xValue);
        double y = getSumValue(yValue);
        double xy = getSumXY(xValue, yValue);
        matrix = new double[]{x,x2,y,xy,xValue.length};
    }

    private void setDelta(double[] matrix){
        delta = (matrix[1]*matrix[4]) - (matrix[0]*matrix[0]);
    }

    private double getDeltaK(){
        double deltaK = 0D;
        if (matrix.length > 0) {
            deltaK = (matrix[3]*matrix[4]) - (matrix[0]*matrix[2]);
        }
        return deltaK/delta;
    }

    private double getDeltaB(){
        double deltaB = 0D;
        if (matrix.length > 0) {
            deltaB = (matrix[1]*matrix[2]) - (matrix[0]*matrix[3]);
        }
        return deltaB/delta;
    }

    public static void main(String[] args) {
//        double[] x = new double[]{1,2,3,4,5,6,7,8,9,10};
        double[] x = new double[]{11,12,13,14,15,16,17,18,19,20};
        double[] y = new double[]{2.0, 4.0, 2.3, 3.8, 3.4, 1.8, 2.0, 1.3, 2.7, 2.1};
        double[] all = new double[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
//        double[] x = new double[]{1,2,3,4,5};
//        double[] y = new double[]{5.3, 6.3, 4.8, 3.8, 3.3};
        LeastSquares ls = new LeastSquares(all, x, y);
        double[] appr = ls.doLeastSquaresApproximation();
        for (double val : appr) {
            System.out.println(val);
        }
        System.out.println("**********************************************************");
        double[] extr = ls.doLeastSquaresExtrapolation();
        for (double val : extr) {
            System.out.println(val);
        }

    }
}
