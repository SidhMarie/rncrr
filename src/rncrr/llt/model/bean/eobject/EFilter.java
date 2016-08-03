package rncrr.llt.model.bean.eobject;

import java.util.Objects;

/**
 * Created by Sidh on 18.04.2016.
 */
public enum EFilter {

    MEAN_VALUE("WF: MEAN VALUE"),
    LEAST_SQUARES("WF: LEAST SQUARES");

    private String name;

    EFilter(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public static EFilter getNameByIndex(Number index){
        EFilter element = null;
        for (EFilter value : values()) {
            if (Objects.equals(value.ordinal(), index)) {
                element = value;
                break;
            }
        }
        return element;
    }

}
