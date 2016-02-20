package rncrr.llt.model.bean.api;

import rncrr.llt.model.bean.Points;

import java.util.List;

/**
 * Created by Sidh on 15.02.2016.
 */
public interface ISourceSeries {

    List<Points> getPoints();

    void addPoints(Points points);

    List<Double> getXPoints();

    List<Double> getYPoints();

}
