package rncrr.llt.view;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SSeries;
import rncrr.llt.model.utils.Config;
import rncrr.llt.view.api.AbstractLChart;
import rncrr.llt.view.api.IChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;

/**
 * Created by Sidh on 24.02.2015.
 */
public class VSourceChart extends AbstractLChart {

    private static final Logger log = LogManager.getLogger(VSourceChart.class);

    @Override
    public void buildingChart(TableView<SSeries> seriesTableView, LineChart<Double, Double> chart) {
        log.trace("Entering into method -> VSourceChart.buildingChart");
        log.trace("Initialize the object lineChart");
        lineChart = FXCollections.observableArrayList();
        chart.setLegendVisible(true);
        log.trace("Try to get the data from the selected row");
        SSeries ss = seriesTableView.getSelectionModel().getSelectedItem();
        log.trace("Try to set the data chart");
        seriesChart = new LineChart.Series<>();
        for (Points points : ss.getPoints()) {
            seriesChart.getData().add(new XYChart.Data<>(points.getX(), points.getY()));
        }
        seriesChart.setName("Series-" + ss.getScanId());
        lineChart.add(seriesChart);
        log.trace("Set the data chart");
        chart.setData(lineChart);
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
