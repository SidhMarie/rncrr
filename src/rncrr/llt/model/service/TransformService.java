package rncrr.llt.model.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.process.dsp.Complex;
import rncrr.llt.model.process.dsp.Transform;
import rncrr.llt.model.process.dsp.Window;
import rncrr.llt.model.service.api.ITransformService;
import rncrr.llt.model.utils.eobject.EWindows;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidh on 06.04.2015.
 */
public class TransformService implements ITransformService {

    private static final Logger log = LogManager.getLogger(TransformService.class);

    private DigitalSeries dSeries;

    /**
     *
     */
    public TransformService() {
        dSeries = new DigitalSeries();
    }

    /**
     *
     * @param sSeries
     * @return
     */
    @Override
    public DigitalSeries getDSeries(SourceSeries sSeries, EWindows windows) {
        log.trace("");
        List<Double> wList = setWindowsData(windows, sSeries.getXPoints());
        List<Double> xList = Transform.inputList(wList);
        int n = xList.size();
        Complex[] x = new Complex[n];
        for (int i = 0; i < n; i++) {
            x[i] = new Complex(xList.get(i), 0d);
        }
        Complex[] y = Transform.directTransform(x);
        double nSpectrum[] = new double[n];
        for(int i = 0; i < n; i++) {
            nSpectrum[i] = y[i].abs() / n; // нормировка
            dSeries.addPoints(new Points(x[i].re(), nSpectrum[i]));
        }
        return dSeries;
    }

//    public DigitalSeries getISeries(){
//        int n = dSeries.getPoints().size();
//        Complex[] x = new Complex[n];
//        for(int i = 0; i < n; i++){
//            x[i] = new Complex()
//        }
//
//    }

    @Override
    public DigitalSeries getDWindows(SourceSeries sSeries, EWindows windows) {
        List<Double> wList = setWindowsData(windows, sSeries.getXPoints());
        int n = wList.size();
        List<Double> yList = sSeries.getYPoints();
        for(int i = 0; i < n; i++){
            dSeries.addPoints(new Points(wList.get(i), yList.get(i)));
        }
        return dSeries;
    }

    private List<Double> setWindowsData(EWindows windows, List<Double> list) {
        List<Double> result = new ArrayList<>();
        switch (windows) {
            case RECTANGULAR :
                for(int i = 0; i<list.size(); i++){
                    result.add(list.get(i) * Window.rectangular(i, list.size()));
                }
                break;
            case GAUSS :
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.gauss(i, list.size()));
                }
                break;
            case HAMMING :
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.hamming(i, list.size()));
                }
                break;
            case HANN :
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.hann(i, list.size()));
                }
                break;
            case BLACKMAN_HARRIS :
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.blackmanHarris(i, list.size()));
                }
                break;
            default:
                for(int i = 0; i < list.size(); i++){
                    result.add(list.get(i) * Window.rectangular(i, list.size()));
                }
        }
        return result;
    }
}
