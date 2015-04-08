package rncrr.llt.model.service;

import rncrr.llt.model.bean.DSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SSeries;
import rncrr.llt.model.process.dsp.Complex;
import rncrr.llt.model.process.dsp.Transform;
import rncrr.llt.model.utils.DspUtil;

import java.util.List;

/**
 * Created by Sidh on 06.04.2015.
 */
public class TransformService {

    private DSeries dSeries;

    public TransformService() {
        dSeries = new DSeries();
    }


    public DSeries getDSeries(SSeries sSeries) {
        List<Points> points = sSeries.getPoints();
        List<Double> xList = DspUtil.getXPoints(points);
        xList = DspUtil.inputList(xList);
        int n = xList.size();
        Complex[] x = new Complex[n];
        for (int i = 0; i < n; i++) {
            x[i] = new Complex(xList.get(i), 0);
        }
        Complex[] y = Transform.directTransform(x);
        for(int i = 0; i < n; i++) {
            dSeries.addPoints(new Points(x[i].re(), y[i].re()));
        }
        return dSeries;
    }

}
