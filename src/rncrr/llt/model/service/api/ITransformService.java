package rncrr.llt.model.service.api;

import rncrr.llt.model.bean.DSeries;
import rncrr.llt.model.bean.SSeries;

/**
 * Created by Sidh on 06.04.2015.
 */
public interface ITransformService {

    DSeries getDSeries(SSeries sSeries);

}
