package rncrr.llt.model.bean;

/**
 * Created by Sidh on 20.02.2015.
 */
public class Points {

    private double x;
    private double y;

    public Points(){}

    public Points(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
