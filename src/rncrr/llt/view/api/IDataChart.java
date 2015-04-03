package rncrr.llt.view.api;

import rncrr.llt.model.bean.SSeries;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TableView;

/**
 * Created by Sidh on 24.02.2015.
 */
public interface IDataChart {

    void buildingChart(TableView<SSeries> seriesTableView, LineChart<Double, Double> profileChart);

    void clearChart(LineChart<Double, Double> profileChart);

    void initChart(LineChart<Double, Double> profileChart);

}
