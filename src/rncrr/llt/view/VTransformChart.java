package rncrr.llt.view;

import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.service.TransformService;
import rncrr.llt.model.utils.eobject.EWindows;
import rncrr.llt.view.api.AbstractChart;
import rncrr.llt.view.utils.VUtil;

/**
 * Created by Sidh on 07.04.2015.
 */
public class VTransformChart extends AbstractChart {

    private static final Logger log = LogManager.getLogger(VTransformChart.class);

    public void buildingSpectrumChart(TableView<SourceSeries> seriesTableView, XYChart<Double, Double> chart, ChoiceBox windowData) {
        log.trace("Entering into method -> VTransformChart.buildingSpectrumChart");
        log.trace("Initialize the object chart");
        this.chart = FXCollections.observableArrayList();
        
        log.trace("Try to get the data from the selected row");
        SourceSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            DigitalSeries d = new TransformService().getDSeries(selectedSeries, windows(windowData));
            seriesChart = new BarChart.Series<>();

            log.trace("Try to set the data chart");
            for (Points points : d.getPoints()) {
                seriesChart.getData().add(new XYChart.Data<>(points.getX(), points.getY()));
            }
            this.chart.add(seriesChart);

            chart.setLegendVisible(false);
            log.trace("Set the data chart");
            chart.setData(this.chart);
        } else {
            VUtil.alertMessage("Should choose a source signal to transform");
        }
    }

    public void buildingWindowChart(TableView<SourceSeries> seriesTableView, XYChart<Double, Double> chart, ChoiceBox windowData) {
        log.trace("Entering into method -> VTransformChart.buildingSpectrumChart");
        log.trace("Initialize the object chart");
        this.chart = FXCollections.observableArrayList();

        log.trace("Try to get the data from the selected row");
        SourceSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            DigitalSeries d = new TransformService().getDWindows(selectedSeries, windows(windowData));
            seriesChart = new BarChart.Series<>();

            log.trace("Try to set the data chart");
            for (Points points : d.getPoints()) {
                seriesChart.getData().add(new XYChart.Data<>(points.getX(), points.getY()));
            }
            this.chart.add(seriesChart);

            chart.setLegendVisible(false);
            log.trace("Set the data chart");
            chart.setData(this.chart);
        } else {
            VUtil.alertMessage("Should choose a source signal to transform");
        }
    }




    private EWindows windows(ChoiceBox windowData){
        return EWindows.getNameByValue(windowData.getValue().toString());
    }
}
