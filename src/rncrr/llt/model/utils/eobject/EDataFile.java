package rncrr.llt.model.utils.eobject;

/**
 * Created by Sidh on 22.10.2015.
 */
public enum EDataFile {

    COUNTS_Y("$COUNTS"),
    COUNTS_XY("$COUNTS_XY");

    private String name;

    EDataFile(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
