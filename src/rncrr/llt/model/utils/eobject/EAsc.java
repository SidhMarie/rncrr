package rncrr.llt.model.utils.eobject;

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
    DATE("%DATE"),
    DETY("%DETY"),
    PNTS("%PNTS"),
    TYPE("%TYPE"),
    BMTY("%BMTY"),
    STEP("%STEP"),
    SSD("%SSD"),
    SPD("%SPD"),
    FLSZ("%FLSZ"),
    DPTH("%DPTH"),
    AXIS("%AXIS"),
    CDEPTH("%CalibrationDepth"),
    CFACTOR("%CalibrationFactor"),
    POINT_START("<"),
    POINT_END(">");

    private String name;

    EAsc(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}

