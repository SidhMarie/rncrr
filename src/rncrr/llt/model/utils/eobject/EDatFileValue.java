package rncrr.llt.model.utils.eobject;

/**
 * Created by Sidh on 18.02.2016.
 */
public enum EDatFileValue {

    COUNTS_Y("COUNTS_Y"),
    COUNTS_XY("COUNTS_XY");

    private String name;

    EDatFileValue(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
