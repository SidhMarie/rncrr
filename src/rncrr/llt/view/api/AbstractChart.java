package rncrr.llt.view.api;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.SourceSeries;

/**
 * Created by Sidh on 07.04.2015.
 */
public abstract class AbstractChart {

    private static final Logger log = LogManager.getLogger(AbstractChart.class);

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

    public void buildingTransformChart(TableView<SourceSeries> seriesTableView, XYChart<Double, Double> chart, ChoiceBox windowData) {

    }

}
