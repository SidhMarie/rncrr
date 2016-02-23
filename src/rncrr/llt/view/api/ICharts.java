package rncrr.llt.view.api;

import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import rncrr.llt.model.bean.AscSourceSeries;
import rncrr.llt.model.bean.api.ISourceSeries;
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
    void initChart(XYChart<Number, Number> chart) throws Exception;

    /**
     *
     * @param chart
     */
    void clearChart(XYChart<Number, Number> chart) throws Exception;

    /**
     *
     * @param tableView
     * @param xychart
     * @param isNew
     */
    void buildingProfileChart(TableView<ISourceSeries> tableView, XYChart<Number, Number> xychart, boolean isNew) throws Exception;

    /**
     *
     * @param tableView
     * @param xychart
     * @param windowData
     * @throws Exception
     */
    XYChart<Number, Number> buildingSpectrumChart(TableView<ISourceSeries> tableView, XYChart<Number, Number> xychart, ChoiceBox windowData) throws  Exception;

    /**
     *
     * @param tableView
     * @param xychart
     * @throws Exception
     */
    XYChart<Number, Number> buildingSpectrumChart(TableView<ISourceSeries> tableView, XYChart<Number, Number> xychart) throws  Exception;

}
