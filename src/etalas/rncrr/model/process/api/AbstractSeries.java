package etalas.rncrr.model.process.api;

import etalas.rncrr.model.bean.Points;
import etalas.rncrr.model.bean.Series;

/**
 * Created by Sidh on 24.02.2015.
 */
public abstract class AbstractSeries {

    protected Points points;
    protected String str;
    protected int bIndex;
    protected int eIndex;

    protected void fillSeries(Series series, String line) {}

    protected String getValue(String line, String key){
        return line.substring(line.indexOf(key) + key.length()).trim();
    }

//    protected String getMeasureType(String type){
//        for(){
//
//        }
//    }

}
