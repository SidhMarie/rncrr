package rncrr.llt.model.utils.eobject;

/**
 * Created by Sidh on 22.10.2015.
 */
public enum EDatFileKey {

    SEPARATOR("|"),
    DATA_TYPE("$DATA_TYPE"),
    SERIES_START("$SERIES_START"),
    SERIES_END("$SERIES_END"),
    SERIES_NAME("$SERIES_NAME"),
    DATA_END("$DATA_END");

    private String name;

    EDatFileKey(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
