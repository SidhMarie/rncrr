package rncrr.llt.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.process.api.ISourceSeries;
import rncrr.llt.model.process.dsp.LeastSquares;
import rncrr.llt.model.service.TransformService;
import rncrr.llt.model.service.api.ITransformService;
import rncrr.llt.model.utils.eobject.ECharts;
import javafx.scene.chart.XYChart;
import rncrr.llt.view.api.ICharts;
import rncrr.llt.view.utils.ChartUtil;
import rncrr.llt.view.utils.VUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by Sidh on 24.02.2015.
 */
public class VCharts implements ICharts {

    private DigitalSeries digitalSeries;
    private ITransformService transformService;
    private XYChart.Series<Number, Number> seriesChart;
    private ObservableList<XYChart.Series<Number, Number>> profileSeries;
    private List<Object> windowsList;
    private Slider slider;

    public VCharts() {
        this.transformService = new TransformService();
        this.profileSeries = FXCollections.observableArrayList();
    }

    @Override
    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    @Override
    public void initChart(XYChart<Number, Number> viewChart) throws Exception {
        ObservableList<XYChart.Series<Number, Number>> chartList = FXCollections.observableArrayList();
        seriesChart = new XYChart.Series<>();
        seriesChart.getData().add(new XYChart.Data<>(0, 0));
        chartList.add(seriesChart);
        viewChart.setLegendVisible(false);
        viewChart.setData(chartList);

        windowsList = new ArrayList<>();
    }

    @Override
    public void clearChart(XYChart<Number, Number> chart) throws Exception {
        chart.setData(FXCollections.<XYChart.Series<Number, Number>>emptyObservableList());
    }

    @Override
    public void buildingProfileChart(TableView<ISourceSeries> seriesTableView, XYChart<Number, Number> xychart, boolean isNew) throws Exception {
        xychart.setLegendVisible(true);
        ISourceSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            if(isNew) {
                profileSeries = FXCollections.observableArrayList();
                seriesChart = new LineChart.Series<>();
                for (Points point : selectedSeries.getPoints()) {
                    seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
                }
            } else {
                seriesChart = new LineChart.Series<>();
                digitalSeries = transformService.getDigitalSeries(selectedSeries, ECharts.RECONSTRUCTED, selectedSeries.getWindow());
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
    public XYChart<Number, Number> buildingSpectrumChart(TableView<ISourceSeries> tableView, XYChart<Number, Number> xychart, ChoiceBox windowData) throws  Exception {
        ISourceSeries selectedSeries = tableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            windowsList = doListWindows(windowData.getValue());
            selectedSeries.setWindow(windowData);
            digitalSeries = transformService.getDigitalSeries(selectedSeries, ECharts.SPECTRUM, windowData.getValue());
            if(digitalSeries != null) {
                ObservableList<XYChart.Series<Number, Number>> spectrumSeries = FXCollections.observableArrayList();
                seriesChart = new LineChart.Series<>();
                for (Points point : digitalSeries.getPoints()) {
                    seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
                }
//                System.out.println(seriesChart.getData().size());
                spectrumSeries.add(seriesChart);
                xychart.setLegendVisible(false);
                xychart.setData(spectrumSeries);
                final int[] dataSize = {seriesChart.getData().size()};
                int ds = seriesChart.getData().size();
                slider.setMax(dataSize[0]);
                slider.setValue(dataSize[0] - 1);

                XYChart.Series<Number, Number> squares = new LineChart.Series<>();
                double[] allSet = new double[ds];
                for(int i = 0; i < ds; i++) {
                    allSet[i] = i;
                }
                System.out.println("===> "+ds);
                for(int i = 0; i<ds; i++){
                    System.out.println("i => "+i +"    value =>"+seriesChart.getData().get(i).getYValue());
                }
                slider.valueProperty().addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
//                        System.out.println("new_val = " + new_val.intValue());
                        System.out.println("=========== xValue");
                        int xValueSize = dataSize[0] - new_val.intValue();
                        double[] xValue = new double[xValueSize];
                        double[] yValue = new double[xValueSize];
                        System.out.println("xValueSize => " + xValueSize);
                        for(int i = 0; i < xValueSize; i++) {
                            int count = dataSize[0] - i;
                            System.out.println();
                            xValue[i] = count;
//                            yValue[i] = seriesChart.getData().get(count).getYValue().doubleValue();

//                            System.out.println(" count => "+count+"  i => "+ i + "  x => " + xValue[i] +"  y => "+seriesChart.getData().get(count).);
                        }

//                        LeastSquares leastSquares = new LeastSquares(allSet, sliderValue,);
                    }
                });

            } else {
                VUtil.alertMessage("Should choose a source signal to inverse transform");
            }
        } else {
            VUtil.alertMessage("Should choose a source signal to transform");
        }
        return xychart;
    }

    @Override
    public XYChart<Number, Number> buildingSpectrumChart(TableView<ISourceSeries> tableView, XYChart<Number, Number> xychart) throws  Exception {
        ISourceSeries selectedSeries = tableView.getSelectionModel().getSelectedItem();
        ObservableList<XYChart.Series<Number, Number>> spectrumSeries = FXCollections.observableArrayList();
        if(selectedSeries != null) {
            for(Object w : windowsList){
                digitalSeries = transformService.getDigitalSeries(selectedSeries, ECharts.SPECTRUM, w);
                if(digitalSeries != null) {
                    seriesChart = new LineChart.Series<>();
                    for (Points point : digitalSeries.getPoints()) {
                        seriesChart.getData().add(new XYChart.Data<>(point.getX(), point.getY()));
                        seriesChart.setName(w.toString());
                    }
                    spectrumSeries.add(seriesChart);
                    xychart.setLegendVisible(true);
                } else {
                    VUtil.alertMessage("Should choose a source signal to inverse transform");
                }
            }
            xychart.setData(spectrumSeries);
        }
        return xychart;
    }

    private List<Object> doListWindows(Object windowValue) {
        List<Object> newList = new ArrayList<>();
        for(Object old : windowsList){
            if(!Objects.equals(old, windowValue)){
                newList.add(old);
            }
        }
        newList.add(windowValue);
        return newList;
    }

}
