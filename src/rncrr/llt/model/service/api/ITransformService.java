package rncrr.llt.model.service.api;

import javafx.scene.control.ChoiceBox;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.process.dsp.Complex;
import rncrr.llt.model.utils.eobject.ECharts;
import rncrr.llt.model.utils.eobject.EWindows;

/**
 * Created by Sidh on 06.04.2015.
 */
public interface ITransformService {

    /**
     *
     * @param sSeries
     * @param windows
     * @return
     */
    void valuesXY(SourceSeries sSeries, EWindows windows);

    /**
     *
     * @param selectedSeries
     * @param eCharts
     * @param windowData
     * @return
     */
    DigitalSeries getDigitalSeries(SourceSeries selectedSeries, ECharts eCharts, ChoiceBox windowData);

}
