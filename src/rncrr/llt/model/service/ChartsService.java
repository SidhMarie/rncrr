package rncrr.llt.model.service;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.bean.eobject.ECharts;
import rncrr.llt.model.bean.eobject.EFilter;
import rncrr.llt.model.dsp.LeastSquares;
import rncrr.llt.model.dsp.MathHelper;
import rncrr.llt.model.service.api.IChartsService;
import rncrr.llt.model.service.api.ITransformService;
import rncrr.llt.model.service.utils.AlertService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by Sidh on 24.02.2015.
 */
public class ChartsService implements IChartsService {

    private DigitalSeries digitalSeries;
    private ITransformService transformService;
    private XYChart.Series<Number, Number> seriesChart;
    private ObservableList<XYChart.Series<Number, Number>> profileSeries;
    private List<Object> windowsList;
    private EFilter filterType;

    private Slider slider;
    private Label meanValueFilter;
    private Label stdValueFilter;

    public ChartsService() {
        this.transformService = new TransformService();
        this.profileSeries = FXCollections.observableArrayList();
    }

    @Override
    public void setChartProperty(Slider slider, Label meanValueFilter, Label stdValueFilter){
        setSliderProperty(slider);
        this.meanValueFilter = meanValueFilter;
        this.stdValueFilter = stdValueFilter;
    }

    @Override
    public void setFilterType(EFilter filterType) {
        this.filterType = filterType;
    }

    @Override
    public void initChart(XYChart<Number, Number> viewChart) throws Exception {
        ObservableList<XYChart.Series<Number, Number>> chartList = FXCollections.observableArrayList();
        seriesChart = new XYChart.Series<>();
        seriesChart.getData().add(new XYChart.Data<Number, Number>(0, 0));
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
    public void buildingProfileChart(TableView<ISourceSeries> tableView, XYChart<Number, Number> xychart, boolean isNew) throws Exception {
        ISourceSeries selectedSeries = tableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            xychart.setLegendVisible(true);
            if(isNew) {
                profileSeries = FXCollections.observableArrayList();
                seriesChart = new LineChart.Series<>();
                for (Points point : selectedSeries.getPoints()) {
                    seriesChart.getData().add(new XYChart.Data<Number, Number>(point.getX(), point.getY()));
                }
            } else {
                seriesChart = new LineChart.Series<>();
                digitalSeries = transformService.getDigitalSeries(selectedSeries, ECharts.RECONSTRUCTED, selectedSeries.getWindow());
                for (Points point : digitalSeries.getPoints()) {
                    seriesChart.getData().add(new XYChart.Data<Number, Number>(point.getX(), point.getY()));
                }
                if(profileSeries.size() == 2)
                    profileSeries.remove(1);
            }
            profileSeries.add(seriesChart);
            xychart.setLegendVisible(false);
            xychart.setData(profileSeries);
            toolTipOnClick(xychart);
        } else {
            AlertService.alertMessage("Should choose a source signal to transform");
        }
    }

    @Override
    public void buildingSpectrumChart(TableView<ISourceSeries> tableView, XYChart<Number, Number> xychart, ChoiceBox windowData) throws  Exception {
        ISourceSeries selectedSeries = tableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            if(windowData == null) {
                buildingSpChart(selectedSeries, xychart);
            } else {
               buildingSpChart(selectedSeries, xychart, windowData);
            }
        } else {
            AlertService.alertMessage("Should choose a source signal to transform");
        }
    }

    private void buildingSpChart(ISourceSeries selectedSeries, XYChart<Number, Number> xychart, ChoiceBox windowData) throws  Exception {
        windowsList = doListWindows(windowData.getValue());
        selectedSeries.setWindow(windowData);
        digitalSeries = transformService.getDigitalSeries(selectedSeries, ECharts.SPECTRUM, windowData.getValue());
        if(digitalSeries != null) {
            ObservableList<XYChart.Series<Number, Number>> spectrumSeries = FXCollections.observableArrayList();
            seriesChart = new LineChart.Series<>();
            for (Points point : digitalSeries.getPoints()) {
                seriesChart.getData().add(new XYChart.Data<Number, Number>(point.getX(), point.getY()));
            }

            int dataSize = seriesChart.getData().size();
            slider.setMax(dataSize);
            slider.setValue(dataSize);

            double[] allSet = new double[dataSize];
            double[] yRealValue = new double[dataSize];
            for(int i = 0; i < dataSize; i++) {
                allSet[i] = i;
                yRealValue[i] = seriesChart.getData().get(i).getYValue().doubleValue();
            }

            int limValueSize = (int) (dataSize*0.9); //todo
            limValueSize = limValueSize > 3 ? limValueSize : 3;
            double limMeanValue, tmp = 0;
            for(int i = dataSize-1; i >= limValueSize; i--) {
                tmp += seriesChart.getData().get(i).getYValue().doubleValue();
            }
            limMeanValue = MathHelper.mean(tmp, (dataSize-limValueSize));

            LeastSquares leastSquares = new LeastSquares();
            slider.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    int xValueSize = dataSize - new_val.intValue();
                    if(xValueSize > 1){
                        double[] xValue = new double[xValueSize];
                        double[] yValue = new double[xValueSize];
                        for (int i = 0; i < xValueSize; i++) {
                            int count = dataSize - i - 1;
                            xValue[i] = count;
                            yValue[i] = yRealValue[count];
                        }
                        XYChart.Series<Number, Number> squares = new LineChart.Series<>();
                        switch (filterType){
                            case MEAN_VALUE:
                                leastSquares.setInputData(allSet, xValue, yValue);
                                double[] result = leastSquares.doMiddleLine();
                                for (double anAllSet : allSet) {
                                    squares.getData().add(new XYChart.Data<Number, Number>(anAllSet, result[0]));
                                }
                                meanValueFilter.setText(String.format("%.3f", result[0]));
                                stdValueFilter.setText(String.format("%.3f", result[1]));
                                transformService.setNoise(result[0]);
                                break;
                            case LEAST_SQUARES:
                                leastSquares.setInputData(allSet, xValue, yValue, limMeanValue);
                                double[] lineNoise = leastSquares.doLeastSquaresExtrapolation();
                                meanValueFilter.setText(String.format("%.3f", limMeanValue));
                                for (int i = 0; i < lineNoise.length; i++) {
                                    squares.getData().add(new XYChart.Data<Number, Number>(allSet[i], lineNoise[i]));
                                    stdValueFilter.setText(String.format("%.3f", lineNoise[i]));
                                }
                                transformService.setNoise(lineNoise, limMeanValue);
                                break;
                        }
                        if(spectrumSeries.size() == 2)
                            spectrumSeries.remove(1);
                        spectrumSeries.add(squares);
                    }
                }
            });
            spectrumSeries.add(seriesChart);
            xychart.setLegendVisible(false);
            xychart.setData(spectrumSeries);
        } else {
            AlertService.alertMessage("Should choose a source signal to inverse transform");
        }
    }

    private void buildingSpChart(ISourceSeries selectedSeries, XYChart<Number, Number> xychart) throws  Exception {
        ObservableList<XYChart.Series<Number, Number>> spectrumSeries = FXCollections.observableArrayList();
        if(selectedSeries != null) {
            for(Object w : windowsList) {
                digitalSeries = transformService.getDigitalSeries(selectedSeries, ECharts.SPECTRUM, w);
                if(digitalSeries != null) {
                    seriesChart = new LineChart.Series<>();
                    for (Points point : digitalSeries.getPoints()) {
                        seriesChart.getData().add(new XYChart.Data<Number, Number>(point.getX(), point.getY()));
                        seriesChart.setName(w.toString());
                    }
                    spectrumSeries.add(seriesChart);
                    xychart.setLegendVisible(true);
                } else {
                    AlertService.alertMessage("Should choose a source signal to inverse transform");
                }
            }
            xychart.setData(spectrumSeries);
        }
    }

    private void setSliderProperty(Slider slider) {
        this.slider = slider;
        slider.setMin(0D);
        slider.setMax(100D);
        slider.setValue(80D);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(9);
        slider.setBlockIncrement(10);
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

    private void toolTipOnClick(XYChart<Number, Number> xychart) {
        final Node[] oldNode = new Node[1];
        for(XYChart.Series<Number, Number> s : xychart.getData()) {
            for (XYChart.Data<Number, Number> data : s.getData()) {
                Tooltip tooltip = new Tooltip("x: "+data.getXValue() + " \ny: " + data.getYValue());
                tooltip.setAutoHide(true);
                data.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(oldNode[0] != data.getNode() && oldNode[0] != null){
                            oldNode[0].setStyle("-fx-background-color: #FF0000, white;");
                        }
                        Node node = (Node) event.getSource();
                        oldNode[0] = node;
                        node.setStyle("-fx-background-color: #0000FF, white; -fx-background-insets: 0,3;");

                        tooltip.show(node, event.getScreenX() + 1, event.getScreenY() + 3);
                    }
                });
            }
        }
    }

}
