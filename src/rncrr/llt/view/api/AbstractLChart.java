package rncrr.llt.view.api;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.SSeries;

/**
 * Created by Sidh on 07.04.2015.
 */
public abstract class AbstractLChart implements IChart {

    private static final Logger log = LogManager.getLogger(AbstractLChart.class);

    protected LineChart.Series<Double, Double> seriesChart;
    protected ObservableList<XYChart.Series<Double, Double>> lineChart;

    /**
     *
     * @param chart
     */
    @Override
    public void initChart(LineChart<Double, Double> chart) {
        log.trace("Entering into method -> AbstractLChart.initChart");
        log.trace("Initialization variable lineChart");
        lineChart = FXCollections.observableArrayList();

        log.trace("Initialization variable seriesChart");
        seriesChart = new LineChart.Series<>();

        log.trace("Add data to the seriesChart");
        seriesChart.getData().add(new XYChart.Data<>(0.0, 0.0));

        log.trace("Add seriesChart to the lineChart");
        lineChart.add(seriesChart);

        log.trace("Set data to chart");
        chart.setLegendVisible(false);
        chart.setData(lineChart);
        log.trace("Initialization is complete chart");
    }

    /**
     *
     * @param chart
     */
    @Override
    public void clearChart(LineChart<Double, Double> chart){
        log.trace("Entering into method -> AbstractLChart.clearChart");
        log.trace("Try to clear chart");
        chart.getData().clear();
        log.trace("Clear is complete chart");
    }

    /**
     *
     * @param seriesTableView
     * @param chart
     */
    @Override
    abstract public void buildingChart(TableView<SSeries> seriesTableView, LineChart<Double, Double> chart);
}
