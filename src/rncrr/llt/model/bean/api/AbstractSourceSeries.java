package rncrr.llt.model.bean.api;

import rncrr.llt.model.bean.Points;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sidh on 17.02.2016.
 */
public abstract class AbstractSourceSeries implements ISourceSeries {

    protected List<Points> points;

    @Override
    public List<Points> getPoints() {
        return points;
    }

    @Override
    public void addPoints(Points points) {
        this.points.add(points);
    }

    @Override
    public List<Double> getXPoints(){
        return points.stream().map(Points::getX).collect(Collectors.toList());
    }

    @Override
    public List<Double> getYPoints(){
        return points.stream().map(Points::getY).collect(Collectors.toList());
    }
}
