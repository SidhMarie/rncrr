package rncrr.llt.view;

import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.service.TransformService;
import rncrr.llt.model.utils.eobject.ECharts;
import rncrr.llt.model.utils.eobject.EWindows;
import rncrr.llt.view.api.AbstractChart;
import rncrr.llt.view.utils.VUtil;

/**
 * Created by Sidh on 07.04.2015.
 */
public class VTransformChart extends AbstractChart {

    private static final Logger log = LogManager.getLogger(VTransformChart.class);

    private DigitalSeries digitalSeries;

    public void buildingTChart(TableView<SourceSeries> seriesTableView, XYChart<Double, Double> chart, ECharts eCharts, ChoiceBox windowData) {
        log.trace("Entering into method -> VTransformChart.buildingSpectrumChart");
        log.trace("Try to get the data from the selected row");
        SourceSeries selectedSeries = seriesTableView.getSelectionModel().getSelectedItem();
        if(selectedSeries != null) {
            setDigitalSeries(selectedSeries, eCharts, windowData);
            buildingTransformChart(chart, digitalSeries);
        } else {
            log.warn("Should choose a source signal to transform");
            VUtil.alertMessage("Should choose a source signal to transform");
        }
    }

    private void setDigitalSeries(SourceSeries selectedSeries, ECharts eCharts, ChoiceBox windowData){
        switch (eCharts) {
            case SPECTRUM :
                digitalSeries = new TransformService().getDSeries(selectedSeries, windows(windowData));
                break;
            case WINDOW :
                digitalSeries = new TransformService().getDWindows(selectedSeries, windows(windowData));
                break;
            default:
                digitalSeries = new TransformService().getDSeries(selectedSeries, windows(windowData));
        }
    }

    private EWindows windows(ChoiceBox windowData){
        return EWindows.getNameByValue(windowData.getValue().toString());
    }
}
