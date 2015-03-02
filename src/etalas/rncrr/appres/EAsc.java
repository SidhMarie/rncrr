package etalas.rncrr.appres;

/**
 * Created by Sidh on 28.02.2015.
 */
public enum EAsc {

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

    private EAsc(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}

