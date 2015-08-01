package rncrr.llt.view.api;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.service.TransformService;
import rncrr.llt.model.utils.eobject.ECharts;
import rncrr.llt.model.utils.eobject.EWindows;

import java.util.List;

/**
 * Created by Sidh on 07.04.2015.
 */
public abstract class AbstractChart {

    private static final Logger log = LogManager.getLogger(AbstractChart.class);

    protected DigitalSeries digitalSeries;
    protected XYChart.Series<Double, Double> seriesChart;
    protected ObservableList<XYChart.Series<Double, Double>> chart;

    /**
     *
     * @param chart
     */
    public void initChart(XYChart<Double, Double> chart) {
        log.trace("Entering into method -> AbstractLChart.initChart");
        log.trace("Initialization variable chart");
        this.chart = FXCollections.observableArrayList();

        log.trace("Initialization variable seriesChart");
        seriesChart = new XYChart.Series<>();

        log.trace("Add data to the seriesChart");
        seriesChart.getData().add(new XYChart.Data<>(0.0, 0.0));

        log.trace("Add seriesChart to the chart");
        this.chart.add(seriesChart);

        log.trace("Set data to chart");
        chart.setLegendVisible(false);
        chart.setData(this.chart);
        log.trace("Initialization is complete chart");
    }

    /**
     *
     * @param chart
     */
    public void clearChart(XYChart<Double, Double> chart){
        log.trace("Entering into method -> AbstractLChart.clearChart");
        log.trace("Try to clear chart");
        chart.getData().clear();
        log.trace("Clear is complete chart");
    }

    protected void buildingChart(XYChart<Double, Double> viewChart, List<Points> pointsList, String flag) {
        log.trace("Entering into method -> VTransformChart.buildingTransformChart");
        log.trace("Try to get the data from the selected row");
        log.trace("Initialize the new object chart");
        if(flag.equals("NEW")) {
            chart = FXCollections.observableArrayList();
        }
        log.trace("Try to set the data chart");
        seriesChart = new LineChart.Series<>();
        for (Points point : pointsList) {
            seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
        }
        chart.add(seriesChart);
        viewChart.setLegendVisible(false);
        log.trace("Set the data chart");
        viewChart.setData(this.chart);
    }

    protected DigitalSeries getDigitalSeries() {
        return digitalSeries;
    }

    protected void setDigitalSeries(SourceSeries selectedSeries, ECharts eCharts, ChoiceBox windowData){
        switch (eCharts) {
            case SPECTRUM :
                digitalSeries = new TransformService().getDSeries(selectedSeries, windows(windowData));
                break;
            case WINDOW :
                digitalSeries = new TransformService().getDWindows(selectedSeries, windows(windowData));
                break;
            default:
                digitalSeries = new TransformService().getDSeries(selectedSeries, windows(windowData));
        }
    }

    private EWindows windows(ChoiceBox windowData){
        return EWindows.getNameByValue(windowData.getValue().toString());
    }

}
