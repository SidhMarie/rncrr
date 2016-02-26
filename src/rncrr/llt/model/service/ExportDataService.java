package rncrr.llt.model.service;

import rncrr.llt.model.bean.ReportSeries;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.process.dsp.Complex;
import rncrr.llt.model.utils.eobject.EWindows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sidh on 25.02.2016.
 */
public class ExportDataService {

    private static Map<Object,List<ReportSeries>> reportData;

    private List<ReportSeries> reportList;

    public ExportDataService() {
        reportData = new HashMap<>();
    }

    public static Map<Object, List<ReportSeries>> getReportData() {
        return reportData;
    }

    public void setSourceData(ISourceSeries series, List<Double> inputList, EWindows windows, Complex[] spectrum){
        reportList = new ArrayList<>();
        ReportSeries reportSeries;
        for(int i = 0; i <inputList.size(); i++){
            reportSeries = new ReportSeries();
            reportSeries.setCount(i + 1);
            if(i < series.getPoints().size()) {
                reportSeries.setSourceX(series.getXPoints().get(i));
                reportSeries.setSourceY(series.getYPoints().get(i));
            } else {
                reportSeries.setSourceX(0D);
                reportSeries.setSourceY(0D);
            }
            reportSeries.setWindowX(inputList.get(i));
            reportSeries.setFrequency(spectrum[i].re());
            reportSeries.setAmplitude(spectrum[i].abs());
            reportList.add(reportSeries);
        }
        if(windows != null){
            reportData.put(windows.name(), reportList);
        } else {
            reportData.put("RECTANGULAR", reportList);
        }
    }



}
