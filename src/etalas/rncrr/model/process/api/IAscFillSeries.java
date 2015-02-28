package etalas.rncrr.model.process.api;

import etalas.rncrr.model.bean.Series;

/**
 * Created by Sidh on 20.02.2015.
 */
public interface IAscFillSeries {

    public void fillSeries(Series series, String line);

}
