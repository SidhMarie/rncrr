package rncrr.llt.view.api;

import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.utils.eobject.ECharts;

import java.util.List;

/**
 * Created by Sidh on 01.08.2015.
 */
public interface ICharts {

    /**
     *
     * @param chart
     */
    void initChart(XYChart<Double, Double> chart) throws Exception;

    /**
     *
     * @param chart
     */
    void clearChart(XYChart<Double, Double> chart) throws Exception;

    /**
     *
     * @param seriesTableView
     * @param xychart
     * @param flag
     */
    void buildingProfileChart(TableView<SourceSeries> seriesTableView, XYChart<Double, Double> xychart, ChoiceBox windowData, String flag) throws Exception;

    /**
     *
     * @param seriesTableView
     * @param xychart
     * @param eCharts
     * @param windowData
     * @param flag
     * @throws Exception
     */
    void buildingSpectrumChart(TableView<SourceSeries> seriesTableView, XYChart<Double, Double> xychart, ECharts eCharts, ChoiceBox windowData, String flag) throws  Exception;

}
