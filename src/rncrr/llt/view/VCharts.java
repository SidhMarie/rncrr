package rncrr.llt.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.service.TransformService;
import rncrr.llt.model.service.api.ITransformService;
import rncrr.llt.model.utils.eobject.ECharts;
import javafx.scene.chart.XYChart;
import rncrr.llt.view.api.ICharts;
import rncrr.llt.view.charts.LineMarkerChart;
import rncrr.llt.view.utils.VUtil;

/**
 * Created by Sidh on 24.02.2015.
 */
public class VCharts implements ICharts {

    private DigitalSeries digitalSeries;
    private ITransformService transformService;
    private XYChart.Series<Number, Number> seriesChart;
    private ObservableList<XYChart.Series<Number, Number>> profileSeries;
    private ObservableList<XYChart.Series<Number, Number>> spectrumSeries;

    private static final Logger log = LogManager.getLogger(VCharts.class);

    public VCharts(ObservableList<XYChart.Series<Number, Number>> profileChart, ObservableList<XYChart.Series<Number, Number>> spectrumChart) {
        transformService = new TransformService();
        this.profileSeries = profileChart;
        this.spectrumSeries = spectrumChart;
    }

    @Override
    public void initChart(XYChart<Number, Number> viewChart) throws Exception {
        log.trace("Entering into method -> VCharts.initChart");
        log.trace("Initialization variable chart");
        ObservableList<XYChart.Series<Number, Number>> chartList = FXCollections.observableArrayList();
        log.trace("Initialization variable seriesChart");
        seriesChart = new XYChart.Series<>();

        log.trace("Add data to the seriesChart");
        seriesChart.getData().add(new XYChart.Data<>(0,0));

        log.trace("Add seriesChart to the chart");
        chartList.add(seriesChart);

        log.trace("Set data to chart");
        viewChart.setLegendVisible(false);
        viewChart.setData(chartList);
        log.trace("Initialization is complete chart");
    }

    @Override
    public void clearChart(XYChart<Number, Number> chart) throws Exception {
        log.trace("Entering into method -> VCharts.clearChart");
        log.trace("Try to clear chart");
        chart.getXAxis().setAutoRanging(true);
        chart.getYAxis().setAutoRanging(true);
        chart.setData(FXCollections.<XYChart.Series<Number, Number>>emptyObservableList());
        log.trace("Clear is complete chart");
    }

    @Override
    public void buildingProfileChart(TableView<SourceSeries> seriesTableView, XYChart<Number, Number> xychart, ChoiceBox windowData, String flag) throws Exception {
        log.trace("Entering into method -> VCharts.buildingViewChart");
        xychart.setLegendVisible(true);
        log.trace("Try to get the data from the selected row");
        SourceSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            if(flag.equals("NEW")) {
                profileSeries = FXCollections.observableArrayList();
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

            profileSeries.add(seriesChart);
            xychart.setLegendVisible(false);
            log.trace("Set the data chart");
            xychart.setData(profileSeries);
            toolTipOnMouse(xychart);
        } else {
            log.warn("Should choose a source signal to transform");
            VUtil.alertMessage("Should choose a source signal to transform");
        }
    }


    @Override
    public void buildingSpectrumChart(TableView<SourceSeries> seriesTableView, LineMarkerChart<Number, Number> xychart, ECharts eCharts, ChoiceBox windowData, String flag) throws  Exception {
        log.trace("Entering into method -> VCharts.buildingViewChart");
        log.trace("Try to get the data from the selected row");
        SourceSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            digitalSeries = transformService.getDigitalSeries(selectedSeries, eCharts, windowData);
            if(digitalSeries != null){
                if(flag.equals("NEW")) {
                    spectrumSeries = FXCollections.observableArrayList();
                }
                seriesChart = new LineChart.Series<>();
                log.trace("Try to set the data chart");
                for (Points point : digitalSeries.getPoints()) {
                    seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
                }
                spectrumSeries.add(seriesChart);
                xychart.setLegendVisible(false);
                log.trace("Set the data chart");
                xychart.setData(spectrumSeries);
                xychart.addVerticalValueMarker(new XYChart.Data<>(50, 100000));
            } else {
                log.warn("Should choose a source signal to inverse transform");
                VUtil.alertMessage("Should choose a source signal to inverse transform");
            }
        } else {
            log.warn("Should choose a source signal to transform");
            VUtil.alertMessage("Should choose a source signal to transform");
        }
    }


    private void toolTipOnMouse(XYChart<Number, Number> xychart){
        for(XYChart.Series<Number, Number> s : xychart.getData()) {
            for (XYChart.Data<Number, Number> data : s.getData()) {
                Tooltip tooltip = new Tooltip("x: "+data.getXValue() + " \ny: " + data.getYValue());
                tooltip.setAutoHide(true);
                data.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Node node = (Node) event.getSource();
                        node.setStyle("-fx-background-color: #0000FF, white;");
                        tooltip.show(node, event.getScreenX() + 1, event.getScreenY() + 1);
                    }
                });
                data.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                                                    @Override
                                                    public void handle(MouseEvent event) {
                                                        tooltip.hide();
                                                    }
                                                }
                );
            }
        }
    }


    private void toolTipOnClick(XYChart<Number, Number> xychart) {
        for(XYChart.Series<Number, Number> s : xychart.getData()) {
            for (XYChart.Data<Number, Number> data : s.getData()) {
                Tooltip tooltip = new Tooltip("x: "+data.getXValue() + " \ny: " + data.getYValue());
                tooltip.setAutoHide(true);
                data.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Node  node =(Node)event.getSource();
                        node.setStyle("-fx-background-color: #0000FF, white;");
                        tooltip.show(node,event.getScreenX() + 1 ,event.getScreenY() + 1);
                    }
                });
            }
        }
    }


}
