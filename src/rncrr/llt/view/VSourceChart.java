package rncrr.llt.view;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.view.api.AbstractChart;
import javafx.collections.FXCollections;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;

/**
 * Created by Sidh on 24.02.2015.
 */
public class VSourceChart extends AbstractChart {

    private static final Logger log = LogManager.getLogger(VSourceChart.class);

    public void buildingSourceChart(TableView<SourceSeries> seriesTableView, XYChart<Double, Double> chart) {
        log.trace("Entering into method -> VSourceChart.buildingSpectrumChart");
        log.trace("Initialize the object chart");
        this.chart = FXCollections.observableArrayList();
        chart.setLegendVisible(true);
        log.trace("Try to get the data from the selected row");
        SourceSeries ss = seriesTableView.getSelectionModel().getSelectedItem();
        log.trace("Try to set the data chart");
        seriesChart = new LineChart.Series<>();
        for (Points points : ss.getPoints()) {
            seriesChart.getData().add(new XYChart.Data<>(points.getX(), points.getY()));
        }
        seriesChart.setName("Series-" + ss.getScanId());
        this.chart.add(seriesChart);
        log.trace("Set the data chart");
        chart.setData(this.chart);
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
