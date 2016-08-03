package rncrr.llt.model.dsp;

import rncrr.llt.model.service.utils.AlertService;

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
    private double[] yValue;
    private double[] allXValue;
    private double[] resultData;
    private double meanValue;

    public LeastSquares() {
    }

    public void setInputData(double[] allXValue, double[] xValue, double[] yValue) {
        this.allXValue = allXValue;
        this.xValue = xValue;
        this.yValue = yValue;
        this.matrix = getMatrix(xValue, yValue);
        this.delta = getDelta();
    }

    public void setInputData(double[] allXValue, double[] xValue, double[] yValue, double mValue) {
        setInputData(allXValue, xValue, yValue);
        this.meanValue = mValue;
    }

    public double[] doLeastSquaresApproximation(){
        resultData = new double[xValue.length];
        if(delta != 0){
            for (int i = 0; i<xValue.length; i++) {
                resultData[i] = getDeltaK()*xValue[i] + getDeltaB();
            }
        } else {
            AlertService.printError("Delta value is out of range. Delta is 0");
        }
        return resultData;
    }

    public double[] doLeastSquaresExtrapolation() {
        resultData = new double[allXValue.length];
        double v;
        if(delta != 0) {
            for (int i = 0; i < allXValue.length; i++) {
                v =  getDeltaK() * allXValue[i] + getDeltaB();
                resultData[i] = v < meanValue ? meanValue : v;
            }
        } else {
            AlertService.printError("Delta value is out of range. Delta is 0");
        }
        return resultData;
    }

    public double[] doMiddleLine() {
        double mean = MathHelper.mean(yValue);
        double std = MathHelper.std(yValue);
        return new double[]{mean,std};
    }

    public void setMeanValue(double meanValue){
        this.meanValue = meanValue;
    }


    private double[] getMatrix(double[] xValue, double[] yValue) {
        double x2 = MathHelper.sumX2(xValue);
        double x = MathHelper.sum(xValue);
        double y = MathHelper.sum(yValue);
        double xy = MathHelper.sumXY(xValue, yValue);
        return new double[]{x,x2,y,xy,xValue.length};
    }

    private double getDelta(){
        return (matrix[1]*matrix[4]) - (matrix[0]*matrix[0]);
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
        LeastSquares ls = new LeastSquares();
        ls.setInputData(all,x,y, 2);
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
