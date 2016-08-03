package rncrr.llt.model.service.api;

import rncrr.llt.model.bean.ExportData;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.bean.eobject.EWindows;

import java.util.List;

/**
 * Created by Sidh on 22.04.2016.
 */
public interface ISourceDataService {

    void setSourceData(ISourceSeries series, List<Double> inputList, EWindows windows, double[] nSpectrum, Double[] frequency);

    List<ExportData> getTransformList();
}
