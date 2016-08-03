package rncrr.llt.model.service.api;

import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.bean.eobject.EFilter;


/**
 * Created by Sidh on 01.08.2015.
 */
public interface IChartsService {


    void setChartProperty(Slider slider, Label meanValueFilter, Label stdValueFilter);

    /**
     *
     * @param filterType
     */
    void setFilterType(EFilter filterType);

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

    void buildingProfileChart(TableView<ISourceSeries> tableView, XYChart<Number, Number> xychart, boolean isNew) throws Exception;

    void buildingSpectrumChart(TableView<ISourceSeries> tableView, XYChart<Number, Number> xychart, ChoiceBox windowData) throws  Exception;

}
