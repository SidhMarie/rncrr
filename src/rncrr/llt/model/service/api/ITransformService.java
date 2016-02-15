package rncrr.llt.model.service.api;

import javafx.scene.control.ChoiceBox;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.AscSourceSeries;
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
    void valuesXY(AscSourceSeries sSeries, EWindows windows);

    /**
     *
     * @param selectedSeries
     * @param eCharts
     * @param windowData
     * @return
     */
    DigitalSeries getDigitalSeries(AscSourceSeries selectedSeries, ECharts eCharts, ChoiceBox windowData);

}
