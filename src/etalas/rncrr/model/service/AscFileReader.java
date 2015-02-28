package etalas.rncrr.model.service;

import etalas.rncrr.model.bean.Series;
import etalas.rncrr.model.process.AscFillSeries;
import etalas.rncrr.model.process.api.AbstractSeries;
import etalas.rncrr.model.process.api.IAscFillSeries;
import etalas.rncrr.model.service.api.AbstractFileReader;


/**
 * Created by Sidh on 27.02.2015.
 */
public class AscFileReader extends AbstractFileReader {

    private int flag;
    private Series series;
    private IAscFillSeries fSeries;

    public AscFileReader() {
        this.flag = 0;
        this.series = new Series();
        this.fSeries = new AscFillSeries();
    }

    @Override
    protected void readLine(String line) {
        if(!line.trim().isEmpty()){
            if(line.equals(AbstractSeries.ESeries.BLOCK_START.getName())){
                flag = 1;
            } else if(line.equals(AbstractSeries.ESeries.BLOCK_END.getName())){
                flag = 2;
            } else if(line.equals(AbstractSeries.ESeries.FILE_END.getName())){
                flag = 0;
            }
        }
    }

    @Override
    protected void fill(String line) {
        switch (flag) {
            case 1 :
                fSeries.fillSeries(series, line);
                break;
            case 2 :
                seriesList.add(series);
                series = new Series();
                break;
        }
    }

}
