package rncrr.llt.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidh on 20.02.2015.
 */
public class DSeries {

    private String subId;
    private List<Points> points;

    public DSeries() {
        this.points = new ArrayList<>();
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public List<Points> getPoints() {
        return points;
    }

    public void addPoints(Points point) {
        this.points.add(point);
    }
}