package rncrr.llt.model.bean;

import rncrr.llt.model.bean.api.ISourceSeries;

import java.util.List;

/**
 * Created by Sidh on 15.02.2016.
 */
public class DatSourceSeries implements ISourceSeries {

    private List<Points> points;

    public List<Points> getPoints() {
        return points;
    }

    public void addPoints(Points points) {
        this.points.add(points);
    }
}
