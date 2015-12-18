package rncrr.llt.model.service;

import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.process.api.AbstractDataFile;
import rncrr.llt.model.utils.eobject.EDataFile;

/**
 * Created by Sidh on 21.10.2015.
 */
public class DataFileService extends AbstractDataFile {

    private int flag;
    private double count;
    private SourceSeries series;

    public DataFileService() {
        this.flag = 0;
        this.count = 0D;
        this.series = new SourceSeries();
    }

    @Override
    protected void readLine(String line) {
        if(line.equals(EDataFile.COUNTS_Y.getName())){
            flag = 1;
        } else if(line.equals(EDataFile.COUNTS_XY.getName())){
            flag = 2;
        } else if(line.equals("")){
            flag = 0;
        }
        fill(line);
    }

    private void fill(String line){
        switch (flag) {
            case 1:
                series.addPoints(new Points(Double.parseDouble(line), count));
                count++;
                break;
            case 2:
                break;
        }
    }

}
