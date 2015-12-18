package rncrr.llt.view.utils;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.util.Duration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sidh on 15.08.2015.
 */
public class ChartUtil {

    public static void toolTipOnClick(XYChart<Number, Number> xychart) {
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

    public static void setPointOnClick(XYChart<Number, Number> xychart, Label fValueX, Label fValueY, Label sValueX, Label sValueY) {

        for(XYChart.Series<Number, Number> s : xychart.getData()) {
            for (XYChart.Data<Number, Number> data : s.getData()) {
                Tooltip tooltip = new Tooltip("x: "+data.getXValue() + " \ny: " + data.getYValue());
                tooltip.setAutoHide(true);
                data.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Node node = (Node) event.getSource();
                        tooltip.show(node, event.getScreenX() + 1, event.getScreenY() + 1);
                        if (fValueX.getText().equals("")) {
                            fValueX.setText(String.format("%.2f", data.getXValue()));
                            fValueY.setText(String.format("%.2f", data.getYValue()));
                        } else if (sValueX.getText().equals("")) {
                            sValueX.setText(String.format("%.2f", data.getXValue()));
                            sValueY.setText(String.format("%.2f", data.getYValue()));
                        } else {
                            fValueX.setText(String.format("%.2f", data.getXValue()));
                            fValueY.setText(String.format("%.2f", data.getYValue()));
                            sValueX.setText("");
                            sValueY.setText("");
                        }
                    }
                });
            }
        }
    }

    public static void toolTipOnMouse(XYChart<Number, Number> xychart){
        for(XYChart.Series<Number, Number> s : xychart.getData()) {
            for (XYChart.Data<Number, Number> data : s.getData()) {
                Tooltip tooltip = new Tooltip("x: "+data.getXValue() + " \ny: " + data.getYValue());
                tooltip.setAutoHide(true);
                data.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Node node = (Node) event.getSource();
                        node.setStyle("-fx-background-color: #0000FF, white;");
                        tooltip.show(node, event.getScreenX() + 1, event.getScreenY() + 1);
                    }
                });
                data.getNode().setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Node node = (Node) event.getSource();
                        node.setStyle("-fx-background-color: #FF0000, white;");
                    }
                });
            }
        }
    }



}