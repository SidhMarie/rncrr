package rncrr.llt.model.utils.eobject;

/**
 * Created by Sidh on 22.10.2015.
 */
public enum EDatFile {

    SEPARATOR("|"),
    DATA_TYPE("$DATA_TYPE"),
    SERIES_START("$SERIES_START"),
    SERIES_END("$SERIES_END"),
    DATA_END("$DATA_END"),
    COUNTS_Y("COUNTS_Y"),
    COUNTS_XY("COUNTS_XY"),
    COUNTS_XYZ("COUNTS_XYZ");

    private String name;

    EDatFile(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
