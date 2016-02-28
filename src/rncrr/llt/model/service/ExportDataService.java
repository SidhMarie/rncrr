package rncrr.llt.model.service;

import rncrr.llt.model.bean.ReportSeries;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.process.ExcelBuilder;
import rncrr.llt.model.process.dsp.Complex;
import rncrr.llt.model.utils.Config;
import rncrr.llt.model.utils.eobject.EWindows;

import java.io.File;
import java.io.FileOutputStream;
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

    public List<ReportSeries> getReportList() {
        return reportList;
    }

    public static void printData() {
        ExcelBuilder eb = new ExcelBuilder(new File(Config.getStringProperty("export.xls.file.name")));
        for(Object key : reportData.keySet()) {
            System.out.println("\n\n****************************************************"+key+"****************************************************");
            List<ReportSeries> list = reportData.get(key);
             eb.createSheet(key.toString(), list);
            for(ReportSeries rs : list){
                System.out.println(rs.getCount() +"  |  "+ rs.getSourceX() +"  |  "+ rs.getSourceY() +"  |  "+ rs.getWindowX() +"  |  "+ rs.getAmplitude() +"  |  "+ rs.getFrequency());
            }
        }
    }

    public void setSourceData(ISourceSeries series, List<Double> inputList, EWindows windows, Complex[] spectrum) {
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
        reportData.put(windows.name(), reportList);
    }

    public void setRebuildData(Complex[] source){
        ReportSeries reportSeries;
        for(int i = 0; i< reportList.size(); i++){
            reportSeries = reportList.get(i);
            reportSeries.setRebuild(source[i].re());
        }
    }
}
