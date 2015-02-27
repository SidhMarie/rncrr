package etalas.rncrr.model.process.api;

import etalas.rncrr.model.bean.Points;
import etalas.rncrr.model.bean.Series;

/**
 * Created by Sidh on 24.02.2015.
 */
public abstract class AbstractSeries {

    protected Points points;
    protected double x, y;
    protected String str;
    protected int bIndex;
    protected int eIndex;

    protected void fillSeries(Series series, String line) {}

    protected String getValue(String line, String key){
        return line.substring(line.indexOf(key) + key.length()).trim();
    }

    public enum ESeries {

        BLOCK_START("$STOM"),
        BLOCK_END("$ENOM"),
        FILE_END("$ENOF"),
        SCAN_ID("Scan ID"),
        BEAM_ENERGY_MV("MV"),
        BEAM_ENERGY_MEV("MeV"),
        BLOCK_DATE("%DATE"),
        BLOCK_BMTY("%BMTY"),
        BLOCK_STEP("%STEP"),
        BLOCK_SSD("%SSD"),
        BLOCK_FLSZ("%FLSZ"),
        BLOCK_DPTH("%DPTH"),
        BLOCK_AXIS("%AXIS"),
        POINT_START("<"),
        POINT_END(">");

        private String name;

        private ESeries(String name){
            this.name = name;
        }

        public String getName(){
            return this.name;
        }

    }

}
