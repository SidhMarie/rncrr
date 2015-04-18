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
import rncrr.llt.view.utils.VUtil;

import java.util.List;

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
        SSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            DSeries d = new TransformService().getDSeries(selectedSeries);
            seriesChart = new LineChart.Series<>();

            log.trace("Try to set the data chart");
            for (Points points : d.getPoints()) {
                seriesChart.getData().add(new XYChart.Data<>(points.getX(), points.getY()));
            }
            lineChart.add(seriesChart);

            chart.setLegendVisible(false);
            log.trace("Set the data chart");
            chart.setData(lineChart);
        } else {
            VUtil.alertMessage("Should choose a source signal to transform");
        }
    }
}
