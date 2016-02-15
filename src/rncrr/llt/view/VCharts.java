package rncrr.llt.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.AscSourceSeries;
import rncrr.llt.model.service.TransformService;
import rncrr.llt.model.service.api.ITransformService;
import rncrr.llt.model.utils.eobject.ECharts;
import javafx.scene.chart.XYChart;
import rncrr.llt.view.api.ICharts;
import rncrr.llt.view.utils.ChartUtil;
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

    public VCharts() {
        this.transformService = new TransformService();
        this.profileSeries = FXCollections.observableArrayList();
        this.spectrumSeries = FXCollections.observableArrayList();
    }


    @Override
    public void initChart(XYChart<Number, Number> viewChart) throws Exception {
        ObservableList<XYChart.Series<Number, Number>> chartList = FXCollections.observableArrayList();
        seriesChart = new XYChart.Series<>();

        seriesChart.getData().add(new XYChart.Data<>(0, 0));

        chartList.add(seriesChart);

        viewChart.setLegendVisible(false);
        viewChart.setData(chartList);
    }

    @Override
    public void clearChart(XYChart<Number, Number> chart) throws Exception {
        chart.setData(FXCollections.<XYChart.Series<Number, Number>>emptyObservableList());
    }

    @Override
    public void buildingProfileChart(TableView<AscSourceSeries> seriesTableView, XYChart<Number, Number> xychart, ChoiceBox windowData, String flag) throws Exception {
        xychart.setLegendVisible(true);
        AscSourceSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            if(flag.equals("NEW")) {
                profileSeries = FXCollections.observableArrayList();
                seriesChart = new LineChart.Series<>();
                for (Points point : selectedSeries.getPoints()) {
                    seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
                }
            } else {
                seriesChart = new LineChart.Series<>();
                digitalSeries = transformService.getDigitalSeries(selectedSeries, ECharts.RECONSTRUCTED, windowData);
                for (Points point : digitalSeries.getPoints()) {
                    seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
                }
            }
            profileSeries.add(seriesChart);
            xychart.setLegendVisible(false);
            xychart.setData(profileSeries);
            ChartUtil.toolTipOnClick(xychart);
        } else {
            VUtil.alertMessage("Should choose a source signal to transform");
        }
    }

    @Override
    public XYChart<Number, Number> buildingSpectrumChart(TableView<AscSourceSeries> seriesTableView, XYChart<Number, Number> xychart, ECharts eCharts, ChoiceBox windowData, String flag) throws  Exception {
        AscSourceSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            digitalSeries = transformService.getDigitalSeries(selectedSeries, eCharts, windowData);
            if(digitalSeries != null){
                if(flag.equals("NEW")) {
                    spectrumSeries = FXCollections.observableArrayList();
                }
                seriesChart = new LineChart.Series<>();
                for (Points point : digitalSeries.getPoints()) {
                    seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
                }
                spectrumSeries.add(seriesChart);
                xychart.setLegendVisible(false);
                xychart.setData(spectrumSeries);
            } else {
                VUtil.alertMessage("Should choose a source signal to inverse transform");
            }
        } else {
            VUtil.alertMessage("Should choose a source signal to transform");
        }
        return xychart;
    }





}
