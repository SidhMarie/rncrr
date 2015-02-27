package etalas.rncrr.view.api;

import etalas.rncrr.model.bean.Series;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TableView;

/**
 * Created by Sidh on 24.02.2015.
 */
public interface IDataChart {

    public void buildingChart(TableView<Series> seriesTableView, LineChart<Double, Double> profileChart);

}
