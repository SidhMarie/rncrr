package rncrr.llt.model.bean;

import java.util.ArrayList;
import java.util.List;

public class SSeries {

    private String scanId;
    private int step;
    private int ssd;
    private int depth;
    private int pnts;
    private String machineName;
    private String beamEnergy;
    private String beamType;
    private String fieldSize;
    private String axis;
    private String date;
    private String dety;
    private String type;
    private double spd;
    private double cDepth;
    private double cFactor;
    private List<Points> points;
    private List<DSeries> dSeries;

    public SSeries() {
        points = new ArrayList<>();
        dSeries = new ArrayList<>();
    }

    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getSsd() {
        return ssd;
    }

    public void setSsd(int ssd) {
        this.ssd = ssd;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getBeamEnergy() {
        return beamEnergy;
    }

    public void setBeamEnergy(String beamEnergy) {
        this.beamEnergy = beamEnergy;
    }

    public String getBeamType() {
        return beamType;
    }

    public void setBeamType(String beamType) {
        this.beamType = beamType;
    }

    public String getFieldSize() {
        return fieldSize;
    }

    public void setFieldSize(String fieldSize) {
        this.fieldSize = fieldSize;
    }

    public String getAxis() {
        return axis;
    }

    public void setAxis(String axis) {
        this.axis = axis;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDety() {
        return dety;
    }

    public void setDety(String dety) {
        this.dety = dety;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPnts() {
        return pnts;
    }

    public void setPnts(int pnts) {
        this.pnts = pnts;
    }

    public double getSpd() {
        return spd;
    }

    public void setSpd(double spd) {
        this.spd = spd;
    }

    public double getcDepth() {
        return cDepth;
    }

    public void setcDepth(double cDepth) {
        this.cDepth = cDepth;
    }

    public double getcFactor() {
        return cFactor;
    }

    public void setcFactor(double cFactor) {
        this.cFactor = cFactor;
    }

    public List<Points> getPoints() {
        return points;
    }

    public void addPoints(Points points) {
        this.points.add(points);
    }

    public List<DSeries> getSubSeries() {
        return dSeries;
    }

    public void addSubSeries(DSeries dSeries) {
        this.dSeries.add(dSeries);
    }
}
