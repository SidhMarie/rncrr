package rncrr.llt.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.process.dsp.Complex;
import rncrr.llt.model.service.TransformService;
import rncrr.llt.model.service.api.ITransformService;
import rncrr.llt.model.utils.eobject.ECharts;
import rncrr.llt.model.utils.eobject.EWindows;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import rncrr.llt.view.api.ICharts;
import rncrr.llt.view.utils.VUtil;

import java.util.List;

/**
 * Created by Sidh on 24.02.2015.
 */
public class VCharts implements ICharts {

    private DigitalSeries digitalSeries;
    private ITransformService transformService;
    private XYChart.Series<Double, Double> seriesChart;
    ObservableList<XYChart.Series<Double, Double>> profileChart;
    ObservableList<XYChart.Series<Double, Double>> spectrumChart;

    private static final Logger log = LogManager.getLogger(VCharts.class);

    public VCharts() {
        transformService = new TransformService();
        profileChart = FXCollections.observableArrayList();
        spectrumChart = FXCollections.observableArrayList();
    }

    @Override
    public void initChart(XYChart<Double, Double> viewChart) throws Exception {
        log.trace("Entering into method -> VCharts.initChart");
        log.trace("Initialization variable chart");
        ObservableList<XYChart.Series<Double, Double>> chart = FXCollections.observableArrayList();
        log.trace("Initialization variable seriesChart");
        seriesChart = new XYChart.Series<>();

        log.trace("Add data to the seriesChart");
        seriesChart.getData().add(new XYChart.Data<>(0.0, 0.0));

        log.trace("Add seriesChart to the chart");
        chart.add(seriesChart);

        log.trace("Set data to chart");
        viewChart.setLegendVisible(false);
        viewChart.setData(chart);
        log.trace("Initialization is complete chart");
    }

    @Override
    public void clearChart(XYChart<Double, Double> chart) throws Exception {
        log.trace("Entering into method -> VCharts.clearChart");
        log.trace("Try to clear chart");
        chart.getData().clear();
        log.trace("Clear is complete chart");
    }

    @Override
    public void buildingProfileChart(TableView<SourceSeries> seriesTableView, XYChart<Double, Double> xychart, ChoiceBox windowData, String flag) throws Exception {
        log.trace("Entering into method -> VCharts.buildingViewChart");
        xychart.setLegendVisible(true);
        log.trace("Try to get the data from the selected row");
        SourceSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            if(flag.equals("NEW")) {
                profileChart = FXCollections.observableArrayList();
                seriesChart = new LineChart.Series<>();
                log.trace("Try to set the data chart");
                for (Points point : selectedSeries.getPoints()) {
                    seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
                }
            } else {
                seriesChart = new LineChart.Series<>();
                digitalSeries = transformService.getDigitalSeries(selectedSeries, ECharts.SOURCE, windowData);
                log.trace("Try to set the data chart");
                for (Points point : digitalSeries.getPoints()) {
                    seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
                }
            }
            profileChart.add(seriesChart);
            xychart.setLegendVisible(false);
            log.trace("Set the data chart");
            xychart.setData(profileChart);
        } else {
            log.warn("Should choose a source signal to transform");
            VUtil.alertMessage("Should choose a source signal to transform");
        }
    }


    @Override
    public void buildingSpectrumChart(TableView<SourceSeries> seriesTableView, XYChart<Double, Double> xychart, ECharts eCharts, ChoiceBox windowData, String flag) throws  Exception {
        log.trace("Entering into method -> VCharts.buildingViewChart");
        log.trace("Try to get the data from the selected row");
        SourceSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            digitalSeries = transformService.getDigitalSeries(selectedSeries, eCharts, windowData);
            if(digitalSeries != null){
                if(flag.equals("NEW")) {
                    spectrumChart = FXCollections.observableArrayList();
                }
                seriesChart = new LineChart.Series<>();
                log.trace("Try to set the data chart");
                for (Points point : digitalSeries.getPoints()) {
                    seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
                }
                spectrumChart.add(seriesChart);
                xychart.setLegendVisible(false);
                log.trace("Set the data chart");
                xychart.setData(spectrumChart);
            } else {
                log.warn("Should choose a source signal to inverse transform");
                VUtil.alertMessage("Should choose a source signal to inverse transform");
            }
        } else {
            log.warn("Should choose a source signal to transform");
            VUtil.alertMessage("Should choose a source signal to transform");
        }
    }


//        for(XYChart.Series<Double, Double> s : profileChart.getData()) {
//            for (XYChart.Data<Double, Double> data : s.getData()) {
//                Tooltip tooltip = new Tooltip(data.getXValue() + " " + data.getYValue());
//                data.getNode().setOnMouseEntered(new EventHandler<Event>() {
//                    @Override
//                    public void handle(Event event) {
//                        Node  node =(Node)event.getSource();
//                        tooltip.show(node,
//                                data.getYValue()+node.getLayoutX()+data.getNode().getScene().getWindow().getX()+node.getScene().getX(),
//                                data.getYValue()+node.getLayoutY()+node.getScene().getWindow().getY()+node.getScene().getX());
//                    }
//                });
////                data.getNode().setOnMouseExited(new EventHandler<Event>() {
////                    @Override
////                    public void handle(Event event) {
////                    }
////                });
//            }
//        }
//    }

}
