package rncrr.llt.view.api;

import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import rncrr.llt.model.bean.AscSourceSeries;
import rncrr.llt.model.utils.eobject.ECharts;

/**
 * Created by Sidh on 01.08.2015.
 */
public interface ICharts {

    /**
     *
     * @param chart
     */
    void initChart(XYChart<Number, Number> chart) throws Exception;

    /**
     *
     * @param chart
     */
    void clearChart(XYChart<Number, Number> chart) throws Exception;

    /**
     *
     * @param seriesTableView
     * @param xychart
     * @param flag
     */
    void buildingProfileChart(TableView<AscSourceSeries> seriesTableView, XYChart<Number, Number> xychart, ChoiceBox windowData, String flag) throws Exception;

    /**
     *
     * @param seriesTableView
     * @param xychart
     * @param eCharts
     * @param windowData
     * @param flag
     * @throws Exception
     */
    XYChart<Number, Number> buildingSpectrumChart(TableView<AscSourceSeries> seriesTableView, XYChart<Number, Number> xychart, ECharts eCharts, ChoiceBox windowData, String flag) throws  Exception;

}
