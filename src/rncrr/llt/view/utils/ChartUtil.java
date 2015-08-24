package rncrr.llt.view.utils;

import javafx.event.EventHandler;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.ScrollEvent;

/**
 * Created by Sidh on 15.08.2015.
 */
public class ChartUtil {

    public static void setupZoomX(XYChart<?,?> chart) {
        chart.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent ev) {
                double zoomFactor = 1.05;
                double deltaY = ev.getDeltaY();
                if (deltaY < 0) {
                    zoomFactor = 2 - zoomFactor;
                }
                NumberAxis xAxisLocal = (NumberAxis)chart.getXAxis();
                xAxisLocal.setUpperBound(xAxisLocal.getUpperBound() * zoomFactor);
                xAxisLocal.setLowerBound(xAxisLocal.getLowerBound() * zoomFactor);
                xAxisLocal.setTickUnit(xAxisLocal.getTickUnit() * zoomFactor);
                ev.consume();
            }
        });

    }
}