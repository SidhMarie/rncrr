package rncrr.llt.model.service;

import rncrr.llt.model.bean.ExportData;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.bean.eobject.EWindows;
import rncrr.llt.model.service.api.IExcelService;
import rncrr.llt.model.service.api.ISourceDataService;
import rncrr.llt.model.service.utils.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sidh on 22.04.2016.
 */
public class SourceDataService implements ISourceDataService {

    private List<ExportData> transformList;
    private static Map<Object,List<ExportData>> transformData;

    public SourceDataService() {
        transformData = new HashMap<>();
    }

    @Override
    public void setSourceData(ISourceSeries series, List<Double> inputList, EWindows windows, double[] nSpectrum, Double[] frequency) {
        transformList = new ArrayList<>();
        ExportData reportSeries;
        for(int i = 0; i <inputList.size(); i++) {
            reportSeries = new ExportData();
            reportSeries.setCount(i + 1);
            if(i < series.getPoints().size()) {
                reportSeries.setSourceX(series.getXPoints().get(i));
                reportSeries.setSourceY(series.getYPoints().get(i));
            } else {
                reportSeries.setSourceX(0D);
                reportSeries.setSourceY(0D);
            }
            reportSeries.setWindowX(inputList.get(i));
            if(i < frequency.length) {
                reportSeries.setFrequency(frequency[i]);
            } else {
                reportSeries.setFrequency(0D);
            }
            if(i < nSpectrum.length){
                reportSeries.setAmplitude(nSpectrum[i]);
            } else {
                reportSeries.setAmplitude(0D);
            }
            transformList.add(reportSeries);
        }
        transformData.put(windows.name(), transformList);
    }

    @Override
    public List<ExportData> getTransformList() {
        return transformList;
    }

    public static void printData() {
        IExcelService eb = new ExcelService(new File(Config.getStringProperty("export.xls.file.name")));
        for(Object key : transformData.keySet()) {
            List<ExportData> list = transformData.get(key);
            eb.createSheet(key.toString(), list);
        }
    }
}
