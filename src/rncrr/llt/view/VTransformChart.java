package rncrr.llt.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.DSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SSeries;
import rncrr.llt.model.service.TransformService;
import rncrr.llt.view.api.AbstractLChart;

/**
 * Created by Sidh on 07.04.2015.
 */
public class VTransformChart extends AbstractLChart {

    private static final Logger log = LogManager.getLogger(VTransformChart.class);

    @Override
    public void buildingChart(TableView<SSeries> seriesTableView, LineChart<Double, Double> chart) {
        log.trace("Entering into method -> VTransformChart.buildingChart");
        log.trace("Initialize the object lineChart");
        lineChart = FXCollections.observableArrayList();
        log.trace("Try to get the data from the selected row");
        ObservableList<SSeries> selectedSeriesList = seriesTableView.getSelectionModel().getSelectedItems();
        log.trace("Try to set the data chart");
        for (SSeries s : selectedSeriesList) {
            seriesChart = new LineChart.Series<>();
            DSeries d = new TransformService().getDSeries(s);
            for (Points points : d.getPoints()) {
                seriesChart.getData().add(new XYChart.Data<>(points.getX(), points.getY()));
            }
            seriesChart.setName("Series-" + s.getScanId());
            lineChart.add(seriesChart);
        }
        log.trace("Set the data chart");
        chart.setData(lineChart);
    }
}
