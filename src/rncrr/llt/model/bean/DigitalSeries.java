package rncrr.llt.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidh on 20.02.2015.
 */
public class DigitalSeries {

    private List<Points> points;

    public DigitalSeries() {
        this.points = new ArrayList<>();
    }

    public List<Points> getPoints() {
        return points;
    }

    public void addPoints(Points point) {
        this.points.add(point);
    }
}
