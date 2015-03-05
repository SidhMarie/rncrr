package etalas.rncrr.appres;

/**
 * Created by Sidh on 03.03.2015.
 */
public enum EMeasureType {

    DDOE("MeasuredDepthDosesForOpenBeam"),     // глубинка с открытым полем для электронов
    DDAE("MeasuredDepthDosesForApplicator"),   // глубинка с апликатором для электронов
    POE("MeasuredProfileForOpenBeam"),         // профиль с открытым полем для электронов
    OPP("OPP"),                                // профиль с открытым полем для фотонов
    OPD("OPD");                                // глубинка с открытым полем для фотонов

    private String name;

    private EMeasureType(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public static String getNameByValue(String key){
        String result = key;
        for(EMeasureType value : values()){
            if(value.getName().equalsIgnoreCase(key)){
                result = value.toString();
                break;
            }
        }
        return result;
    }

}
