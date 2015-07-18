package rncrr.llt.model.service.api;

import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.utils.eobject.EWindows;

/**
 * Created by Sidh on 06.04.2015.
 */
public interface ITransformService {

    DigitalSeries getDSeries(SourceSeries sSeries, EWindows windows);

}
