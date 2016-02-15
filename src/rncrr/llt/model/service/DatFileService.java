package rncrr.llt.model.service;

import rncrr.llt.model.bean.DatSourceSeries;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.process.api.AbstractDataFile;
import rncrr.llt.model.utils.eobject.EDatFile;

/**
 * Created by Sidh on 21.10.2015.
 */
public class DatFileService extends AbstractDataFile {

    private int flag;
    private double count;
    private DatSourceSeries series;

    public DatFileService() {
        this.flag = 0;
        this.count = 0D;
        this.series = new DatSourceSeries();
    }

    @Override
    protected void readLine(String line) {
        if(line.equals(EDatFile.SERIES_START.getName())){
            flag = 1;
        } else if(line.equals(EDatFile.SERIES_END.getName())){
            flag = 2;
        } else if(line.equals(EDatFile.DATA_END.getName())){
            flag = 0;
        }
        fill(line);
    }

    private void fill(String line){
        switch (flag) {
            case 1:
                fillSeries(series, line);
                break;
            case 2:
                seriesList.add(series);
                series = new DatSourceSeries();
                break;
        }
    }

    private void setTypeSeries(String line){

    }

    private void fillSeries(DatSourceSeries series, String line) {
        if(line.matches("[a-zA-Z]+"))
            return;
        if(!line.trim().equals("")) {
            series.addPoints(new Points(Double.parseDouble(line), count));
            count++;
        }
    }

}
