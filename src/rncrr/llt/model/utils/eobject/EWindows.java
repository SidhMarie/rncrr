package rncrr.llt.model.utils.eobject;

/**
 * Created by Sidh on 15.04.2015.
 */
public enum EWindows {

    RECTANGULAR, GAUSS, HAMMING, HANN, BLACKMAN_HARRIS;

    public static EWindows getNameByValue(String key){
        for(EWindows value : values()){
            if(value.toString().equalsIgnoreCase(key)){
                return value;
            }
        }
        return RECTANGULAR;
    }
}
