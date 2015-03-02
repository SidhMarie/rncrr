package etalas.rncrr.view;


import etalas.rncrr.model.bean.Points;
import etalas.rncrr.model.bean.Series;
import etalas.rncrr.model.process.Config;
import etalas.rncrr.view.api.IDataChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;

/**
 * Created by Sidh on 24.02.2015.
 */
public class VDataChart implements IDataChart {

    private ObservableList<XYChart.Series<Double, Double>> lineChart;
    private LineChart.Series<Double, Double> seriesChart;

    @Override
    public void initChart(LineChart<Double, Double> profileChart){
        profileChart.setTitle(Config.getStringProperty("line.chart.name"));
        lineChart = FXCollections.observableArrayList();
        seriesChart = new LineChart.Series<>();
        seriesChart.getData().add(new XYChart.Data<>(0.0, 0.0));
        seriesChart.setName("Series");
        lineChart.add(seriesChart);
        profileChart.setData(lineChart);
    }

    @Override
    public void buildingChart(TableView<Series> seriesTableView, LineChart<Double, Double> profileChart){
        lineChart = FXCollections.observableArrayList();
        ObservableList<Series> selectedSeriesList = seriesTableView.getSelectionModel().getSelectedItems();
        for(Series s : selectedSeriesList) {
            seriesChart = new LineChart.Series<>();
            for (Points points : s.getPoints()) {
                seriesChart.getData().add(new XYChart.Data<>(points.getX(), points.getY()));
            }
            seriesChart.setName("Series-" + s.getScanId());
            lineChart.add(seriesChart);
        }
        profileChart.setData(lineChart);

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
    }

    @Override
    public void clearChart(LineChart<Double, Double> profileChart){
        profileChart.setData(FXCollections.observableArrayList());
    }

}
