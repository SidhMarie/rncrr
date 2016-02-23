package rncrr.llt.model.service.api;

import javafx.scene.control.ChoiceBox;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.AscSourceSeries;
import rncrr.llt.model.bean.api.ISourceSeries;
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
    void valuesXY(ISourceSeries sSeries, EWindows windows);

    /**
     *
     * @param selectedSeries
     * @param eCharts
     * @param windowValue
     * @return
     */
    DigitalSeries getDigitalSeries(ISourceSeries selectedSeries, ECharts eCharts, Object windowValue);

}
