package rncrr.llt.model.service;


import javafx.scene.control.ChoiceBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.DigitalSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.process.dsp.Complex;
import rncrr.llt.model.process.dsp.Transform;
import rncrr.llt.model.process.dsp.Window;
import rncrr.llt.model.service.api.ITransformService;
import rncrr.llt.model.utils.eobject.ECharts;
import rncrr.llt.model.utils.eobject.EWindows;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sidh on 06.04.2015.
 */
public class TransformService implements ITransformService {

    private static final Logger log = LogManager.getLogger(TransformService.class);

    private Complex[] x;
    private Complex[] y;
    private DigitalSeries dSeries;

    public TransformService() {}

    @Override
    public void valuesXY(SourceSeries sSeries, EWindows windows) {
        List<Double> wList = setWindowsData(windows, sSeries.getXPoints());
        List<Double> xList = Transform.inputList(wList);
        int n = xList.size();
        x = new Complex[n];
        for (int i = 0; i < n; i++) {
            x[i] = new Complex(xList.get(i), 0d);
        }
        y = Transform.directTransform(x);
    }

    @Override
    public DigitalSeries getDigitalSeries(SourceSeries selectedSeries, ECharts eCharts, ChoiceBox windowData) {
        try{
            switch (eCharts) {
                case SOURCE :
                    return getSourceSeries(selectedSeries, windows(windowData));
                case SPECTRUM :
                    return getSpectrum(selectedSeries, windows(windowData));
                case WINDOW :
                    return getDWindows(selectedSeries, windows(windowData));
                default:
                    return null;
            }
        } catch (Exception e) {
            dSeries = null;
        }
        return null;
    }

    private EWindows windows(ChoiceBox windowData){
        return EWindows.getNameByValue(windowData.getValue().toString());
    }

    private DigitalSeries getSpectrum(SourceSeries sSeries, EWindows windows) {
        valuesXY(sSeries, windows);
        int n = x.length/2;
        double[] nSpectrum = new double[n];
        dSeries = new DigitalSeries();
        for(int i = 0; i < n; i++) {
            nSpectrum[i] = y[i].ps() / n; // нормировка
            dSeries.addPoints(new Points(i, nSpectrum[i]));
        }
        return dSeries;
    }

    private DigitalSeries getSourceSeries(SourceSeries sSeries, EWindows windows) {
        Complex[] source = Transform.inverseTransform(y);
        dSeries = new DigitalSeries();
        List<Double> yPoint = sSeries.getYPoints();
        int ySize = yPoint.size();
        for(int i = 0; i < source.length; i++){
            if(i < ySize){
                dSeries.addPoints(new Points(source[i].re(), yPoint.get(i)));
            } else {
                dSeries.addPoints(new Points(source[i].re(), 0D));
            }
        }
        return dSeries;
    }

    private DigitalSeries getDWindows(SourceSeries sSeries, EWindows windows) {
        List<Double> wList = setWindowsData(windows, sSeries.getXPoints());
        int n = wList.size();
        List<Double> yList = sSeries.getYPoints();
        dSeries = new DigitalSeries();
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
