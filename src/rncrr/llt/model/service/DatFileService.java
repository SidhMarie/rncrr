package rncrr.llt.model.service;

import rncrr.llt.model.bean.DatSourceSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.process.api.ISourceSeries;
import rncrr.llt.model.process.api.AbstractDataFile;
import rncrr.llt.model.utils.eobject.EDatFileKey;
import rncrr.llt.model.utils.eobject.EDatFileValue;
import rncrr.llt.view.utils.VUtil;

import java.util.ArrayList;

/**
 * Created by Sidh on 21.10.2015.
 */
public class DatFileService extends AbstractDataFile {

    private double count;
    private DatSourceSeries series;

    public DatFileService() {
        this.count = 0D;
        this.series = new DatSourceSeries();
        this.dataList = new ArrayList<>();
    }

    @Override
    protected void readList() {
        if(dataList.size() == 0) {
            VUtil.alertWarning("File is empty");
            return;
        }
        for(String line : dataList){
            if(line.equals(EDatFileKey.SERIES_START.getName())) {
                // do nothing
            } else if(line.equals(EDatFileKey.SERIES_END.getName())) {
                seriesList.add(series);
                series = new DatSourceSeries();
            } else if(line.equals(EDatFileKey.DATA_END.getName())) {
                // do nothing
            } else {
                fillSeries(line);
            }
        }

        if(seriesList.size() == 0){
            seriesList.add(series);
        }

        setPropertySeries();
    }

    private void setPropertySeries(){
        int countSeries = 1;
        for(ISourceSeries s : seriesList) {
            DatSourceSeries ds = (DatSourceSeries)s;
            if(ds.getDataType() == null) {
                ds.setDataType(EDatFileValue.COUNTS_Y.getName());
            }
            if(ds.getSeriesName() == null) {
                ds.setSeriesName(String.format("Series %d", countSeries));
                countSeries++;
            }
        }
    }

    private void addPoints(String line, int dtype){
        if(line.matches("[a-zA-Z]+")){
            System.err.println("");
            return;
        }
        String serviseString, xPoint, yPoint;
        if(!line.trim().equals("")) {
            switch (dtype) {
                case 1:
                    if (!line.contains(EDatFileKey.SEPARATOR.getName())) {
                        series.addPoints(new Points(count, Double.parseDouble(line.trim())));
                    } else {
                        line = line.substring(0, line.indexOf(EDatFileKey.SEPARATOR.getName()));
                        series.addPoints(new Points(count, Double.parseDouble(line.trim())));
                    }
                    count++;
                    break;
                case 2:
                    xPoint = line.substring(0, line.indexOf(EDatFileKey.SEPARATOR.getName()));
                    serviseString = line.substring(line.indexOf(EDatFileKey.SEPARATOR.getName(), line.length()));
                    if(serviseString.contains(EDatFileKey.SEPARATOR.getName())) {
                        yPoint = serviseString.substring(0, serviseString.indexOf(EDatFileKey.SEPARATOR.getName()));
                    } else {
                        yPoint = serviseString;
                    }
                    series.addPoints(new Points(Double.parseDouble(xPoint), Double.parseDouble(yPoint)));
                    break;
                case 3:
                    break;
            }
        }
    }

    private void fillSeries(String line) {
        int dtype = 1;
        boolean isData = true;
        for(EDatFileKey value : EDatFileKey.values()) {
            if (line.contains(value.getName())) {
                switch (value) {
                    case DATA_TYPE :
                        if(line.contains(EDatFileValue.COUNTS_Y.getName())) {
                            series.setDataType(EDatFileValue.COUNTS_Y.getName());
                            dtype = 1;
                        } else if(line.contains(EDatFileValue.COUNTS_XY.getName())) {
                            series.setDataType(EDatFileValue.COUNTS_XY.getName());
                            dtype = 2;
                        }
                        break;
                    case SERIES_NAME :
                        series.setSeriesName(getValue(line, EDatFileKey.SERIES_NAME.getName()));
                        break;
                }
                isData = false;
            }
        }
        if(isData){
            addPoints(line, dtype);
        }
    }

}
