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
    private ObservableList<XYChart.Series<Double, Double>> chart;

    private static final Logger log = LogManager.getLogger(VCharts.class);

    public VCharts() {
        transformService = new TransformService();
    }

    @Override
    public void initChart(XYChart<Double, Double> chart) throws Exception {
        log.trace("Entering into method -> VCharts.initChart");
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

    @Override
    public void clearChart(XYChart<Double, Double> chart) throws Exception {
        log.trace("Entering into method -> VCharts.clearChart");
        log.trace("Try to clear chart");
        chart.getData().clear();
        log.trace("Clear is complete chart");
    }

    @Override
    public void buildingViewChart(TableView<SourceSeries> seriesTableView, XYChart<Double, Double> xychart, String flag) throws Exception {
        log.trace("Entering into method -> VCharts.buildingViewChart");
        xychart.setLegendVisible(true);
        log.trace("Try to get the data from the selected row");
        SourceSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            buildingChart(xychart, selectedSeries.getPoints(), flag);
        } else {
            log.warn("Should choose a source signal to transform");
            VUtil.alertMessage("Should choose a source signal to transform");
        }
    }


    @Override
    public void buildingViewChart(TableView<SourceSeries> seriesTableView, XYChart<Double, Double> chart, ECharts eCharts, ChoiceBox windowData, String flag) throws  Exception {
        log.trace("Entering into method -> VCharts.buildingViewChart");
        log.trace("Try to get the data from the selected row");
        SourceSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            setDigitalSeries(selectedSeries, eCharts, windowData);
            if(digitalSeries != null){
                buildingChart(chart, digitalSeries.getPoints(), flag);
            } else {
                log.warn("Should choose a source signal to inverse transform");
                VUtil.alertMessage("Should choose a source signal to inverse transform");
            }
        } else {
            log.warn("Should choose a source signal to transform");
            VUtil.alertMessage("Should choose a source signal to transform");
        }
    }

    /**
     *
     * @param viewChart
     * @param pointsList
     * @param flag
     */
    private void buildingChart(XYChart<Double, Double> viewChart, List<Points> pointsList, String flag) throws Exception {
        log.trace("Entering into method -> VCharts.buildingTransformChart");
        log.trace("Try to get the data from the selected row");
        log.trace("Initialize the new object chart");
        if(flag.equals("NEW")) {
            chart = FXCollections.observableArrayList();
        }
        log.trace("Try to set the data chart");
        seriesChart = new LineChart.Series<>();
        for (Points point : pointsList) {
            seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
        }
        chart.add(seriesChart);
        viewChart.setLegendVisible(false);
        log.trace("Set the data chart");
        viewChart.setData(chart);
    }


    /**
     *
     * @param selectedSeries
     * @param eCharts
     * @param windowData
     */
    private void setDigitalSeries(SourceSeries selectedSeries, ECharts eCharts, ChoiceBox windowData) {
        try{
            switch (eCharts) {
                case SOURCE :
                    digitalSeries = transformService.getSourceSeries(selectedSeries, windows(windowData));
                    break;
                case SPECTRUM :
                    digitalSeries = transformService.getSpectrum(selectedSeries, windows(windowData));
                    break;
                case WINDOW :
                    digitalSeries = transformService.getDWindows(selectedSeries, windows(windowData));
                    break;
                default:
                    digitalSeries = null;
            }
        } catch (Exception e) {
            digitalSeries = null;
        }
    }

    /**
     *
     * @param windowData
     * @return
     */
    private EWindows windows(ChoiceBox windowData){
        return EWindows.getNameByValue(windowData.getValue().toString());
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
