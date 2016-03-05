package rncrr.llt.model.service.api;

import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.process.api.ISourceSeries;
import rncrr.llt.model.utils.eobject.ECharts;

/**
 * Created by Sidh on 06.04.2015.
 */
public interface ITransformService {

    /**
     *
     * @param selectedSeries
     * @param eCharts
     * @param windowValue
     * @return
     */
    DigitalSeries getDigitalSeries(ISourceSeries selectedSeries, ECharts eCharts, Object windowValue);

}
