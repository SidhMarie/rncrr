package rncrr.llt.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.utils.eobject.EAsc;
import rncrr.llt.model.utils.eobject.EMeasureType;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.SSeries;
import rncrr.llt.model.service.api.AbstractFileReader;

import java.util.Objects;


/**
 * Created by Sidh on 27.02.2015.
 */
public class AscFileReader extends AbstractFileReader {

    private int flag;
    private SSeries series;
    private String serviceString;
    private static final Logger log = LogManager.getLogger(AscFileReader.class);

    /**
     *
     */
    public AscFileReader() {
        log.trace("Entering into class -> AscFileReader");
        log.trace("Initialize the variable -> flag = 0");
        this.flag = 0;
        log.trace("Initialize the new object -> SSeries");
        this.series = new SSeries();
    }

    /**
     *
     * @param line
     */
    @Override
    protected void readLine(String line) {
        if(!line.trim().isEmpty()) {
            if(line.equals(EAsc.BLOCK_START.getName())){
                flag = 1;
            } else if(line.equals(EAsc.BLOCK_END.getName())){
                flag = 2;
            } else if(line.equals(EAsc.FILE_END.getName())){
                flag = 0;
            }
        }
    }

    /**
     *
     * @param line
     */
    @Override
    protected void fill(String line) {
        switch (flag) {
            case 1 :
                fillSeries(series, line);
                break;
            case 2 :
                seriesList.add(series);
                series = new SSeries();
                break;
        }
    }

    /**
     *
     * @param series
     * @param line
     */
    public void fillSeries(SSeries series, String line) {
        for(EAsc value : EAsc.values()) {
            if(line.contains(value.getName())) {
                switch (value){
                    case SCAN_ID :
                        series.setScanId(line.substring(line.indexOf(":") + 1).trim());
                        break;
                    case BEAM_ENERGY_MV :
                        series.setBeamEnergy(line.substring(line.indexOf("-") + 1).trim());
                        series.setMachineName(line.substring(line.indexOf("#") + 1, line.indexOf("-")).trim());
                        break;
                    case BEAM_ENERGY_MEV :
                        series.setBeamEnergy(line.substring(line.indexOf("-") + 1).trim());
                        series.setMachineName(line.substring(line.indexOf("#") + 1, line.indexOf("-")).trim());
                        break;
                    case DATE:
                        series.setDate(getValue(line, value.getName()));
                        break;
                    case DETY:
                        series.setDety(getValue(line, value.getName()));
                        break;
                    case PNTS:
                        series.setPnts(Integer.parseInt(getValue(line, value.getName())));
                        break;
                    case TYPE:
                        String type = getValue(line, value.getName());
                        series.setType(EMeasureType.getNameByValue(type));
                        break;
                    case BMTY:
                        series.setBeamType(getValue(line, value.getName()));
                        break;
                    case STEP:
                        series.setStep(Integer.parseInt(getValue(line, value.getName())));
                        break;
                    case SSD:
                        series.setSsd(Integer.parseInt(getValue(line, value.getName())));
                        break;
                    case SPD:
                        series.setSpd(Double.parseDouble(getValue(line, value.getName())));
                        break;
                    case FLSZ:
                        series.setFieldSize(getValue(line, value.getName()));
                        break;
                    case DPTH:
                        series.setDepth(Integer.parseInt(getValue(line, value.getName())));
                        break;
                    case AXIS:
                        series.setAxis(getValue(line, value.getName()));
                        break;
                    case CDEPTH:
                        series.setcDepth(Double.parseDouble(getValue(line, value.getName())));
                        break;
                    case CFACTOR:
                        series.setcFactor(Double.parseDouble(getValue(line, value.getName())));
                        break;
                    case POINT_START :
                        addPointsData(series, line);
                        break;
                }
            }
        }
    }

    /**
     *
     * @param series
     * @param line
     */
    private void addPointsData(SSeries series, String line) {
        serviceString = line.substring(2, 29);  // стандартная строка, всегда одного размера <-000.0 -000.0 +000.0 +000.0>
        if(Objects.equals(series.getType(), EMeasureType.DDOE.name())
                || Objects.equals(series.getType(), EMeasureType.DDAE.name())
                || Objects.equals(series.getType(), EMeasureType.OPD.name()) )
        {
            fillPoint(series,
                    Double.parseDouble(serviceString.substring(15,21)),
                    Double.parseDouble(serviceString.substring(22)));
        } else if(Objects.equals(series.getType(), EMeasureType.POE.name())
                || Objects.equals(series.getType(), EMeasureType.OPP.name()) )
        {
            fillPoint(series,
                    Double.parseDouble(serviceString.substring(0,6)),
                    Double.parseDouble(serviceString.substring(22)));
        }
    }

    /**
     *
     * @param series
     * @param x
     * @param y
     */
    private void fillPoint(SSeries series, double x, double y){
        series.addPoints(new Points(x,y));
    }

}
