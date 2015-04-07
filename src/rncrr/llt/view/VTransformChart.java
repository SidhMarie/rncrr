package rncrr.llt.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import rncrr.llt.model.bean.SSeries;
import rncrr.llt.model.utils.Config;
import rncrr.llt.view.api.AbstractLChart;
import rncrr.llt.view.api.IChart;

/**
 * Created by Sidh on 07.04.2015.
 */
public class VTransformChart extends AbstractLChart {

    @Override
    public void buildingChart(TableView<SSeries> seriesTableView, LineChart<Double, Double> chart) {

    }
}
