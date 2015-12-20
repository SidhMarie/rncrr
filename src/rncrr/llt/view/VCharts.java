package rncrr.llt.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
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
import rncrr.llt.view.utils.ChartUtil;
import rncrr.llt.view.utils.VUtil;

import java.util.Set;


/**
 * Created by Sidh on 24.02.2015.
 */
public class VCharts implements ICharts {

    private static final Logger log = LogManager.getLogger(VCharts.class);

    private DigitalSeries digitalSeries;
    private ITransformService transformService;
    private XYChart.Series<Number, Number> seriesChart;
    private ObservableList<XYChart.Series<Number, Number>> profileSeries;
    private ObservableList<XYChart.Series<Number, Number>> spectrumSeries;

    public VCharts() {
        this.transformService = new TransformService();
        this.profileSeries = FXCollections.observableArrayList();
        this.spectrumSeries = FXCollections.observableArrayList();
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
                digitalSeries = transformService.getDigitalSeries(selectedSeries, ECharts.RECONSTRUCTED, windowData);
                log.trace("Try to set the data chart");
                for (Points point : digitalSeries.getPoints()) {
                    seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
                }
            }
            profileSeries.add(seriesChart);
            xychart.setLegendVisible(false);
            log.trace("Set the data chart");
            xychart.setData(profileSeries);
            ChartUtil.toolTipOnClick(xychart);
        } else {
            log.warn("Should choose a source signal to transform");
            VUtil.alertMessage("Should choose a source signal to transform");
        }
    }

    @Override
    public XYChart<Number, Number> buildingSpectrumChart(TableView<SourceSeries> seriesTableView, XYChart<Number, Number> xychart, ECharts eCharts, ChoiceBox windowData, String flag) throws  Exception {
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
            } else {
                log.warn("Should choose a source signal to inverse transform");
                VUtil.alertMessage("Should choose a source signal to inverse transform");
            }
        } else {
            log.warn("Should choose a source signal to transform");
            VUtil.alertMessage("Should choose a source signal to transform");
        }
        return xychart;
    }





}
