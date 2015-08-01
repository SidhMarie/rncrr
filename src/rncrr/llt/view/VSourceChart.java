package rncrr.llt.view;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.view.api.AbstractChart;
import javafx.collections.FXCollections;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import rncrr.llt.view.utils.VUtil;

/**
 * Created by Sidh on 24.02.2015.
 */
public class VSourceChart extends AbstractChart {

    private static final Logger log = LogManager.getLogger(VSourceChart.class);

    public void buildingSChart(TableView<SourceSeries> seriesTableView, XYChart<Double, Double> xychart, String flag) {
        log.trace("Entering into method -> VSourceChart.buildingSpectrumChart");
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

    public void buildingSChart(XYChart<Double, Double> xychart, String flag) {
        log.trace("Entering into method -> VSourceChart.buildingSpectrumChart");
        xychart.setLegendVisible(true);
        log.trace("Try to get the data from the selected row");
        DigitalSeries dSeries = getDigitalSeries();
        if(dSeries != null) {
            buildingChart(xychart, dSeries.getPoints(), flag);
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
