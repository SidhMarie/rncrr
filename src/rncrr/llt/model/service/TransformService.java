package rncrr.llt.model.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.DSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SSeries;
import rncrr.llt.model.process.dsp.Complex;
import rncrr.llt.model.process.dsp.Transform;
import rncrr.llt.model.service.api.ITransformService;
import java.util.List;

/**
 * Created by Sidh on 06.04.2015.
 */
public class TransformService implements ITransformService {

    private static final Logger log = LogManager.getLogger(TransformService.class);

    private DSeries dSeries;

    public TransformService() {
        dSeries = new DSeries();
    }

    @Override
    public DSeries getDSeries(SSeries sSeries) {
        log.trace("");
        List<Double> xList = sSeries.getXPoints();
        xList = Transform.inputList(xList);
        int n = xList.size();
        Complex[] x = new Complex[n];
        for (int i = 0; i < n; i++) {
            x[i] = new Complex(xList.get(i), 0);
        }
        Complex[] y = Transform.directTransform(x);
        for(int i = 0; i < n; i++) {
            dSeries.addPoints(new Points(x[i].re(), y[i].abs()));
        }
        return dSeries;
    }

}
