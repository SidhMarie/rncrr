package rncrr.llt.model.service.api;

import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.process.dsp.Complex;
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
     * @param sSeries
     * @param windows
     * @return
     */
    DigitalSeries getSpectrum(SourceSeries sSeries, EWindows windows);

    /**
     *
     * @return
     */
    DigitalSeries getSourceSeries(SourceSeries sSeries, EWindows windows);

    /**
     *
     * @param sSeries
     * @param windows
     * @return
     */
    DigitalSeries getDWindows(SourceSeries sSeries, EWindows windows);



    Complex[] getX();

    Complex[] getY();

}
