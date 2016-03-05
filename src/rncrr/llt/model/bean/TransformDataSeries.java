package rncrr.llt.model.bean;

/**
 * Created by Sidh on 26.02.2016.
 */
public class TransformDataSeries {

    private int count;
    private Double sourceX;
    private Double sourceY;
    private Double windowX;
    private Double frequency;
    private Double amplitude;
    private Double filter;
    private Double rebuild;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getSourceX() {
        return sourceX;
    }

    public void setSourceX(Double sourceX) {
        this.sourceX = sourceX;
    }

    public Double getSourceY() {
        return sourceY;
    }

    public void setSourceY(Double sourceY) {
        this.sourceY = sourceY;
    }

    public Double getWindowX() {
        return windowX;
    }

    public void setWindowX(Double windowX) {
        this.windowX = windowX;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public Double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(Double amplitude) {
        this.amplitude = amplitude;
    }

    public Double getFilter() {
        return filter;
    }

    public void setFilter(Double filter) {
        this.filter = filter;
    }

    public Double getRebuild() {
        return rebuild;
    }

    public void setRebuild(Double rebuild) {
        this.rebuild = rebuild;
    }
}
