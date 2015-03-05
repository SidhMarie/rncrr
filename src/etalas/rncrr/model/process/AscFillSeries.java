package etalas.rncrr.model.process;

import etalas.rncrr.appres.EAsc;
import etalas.rncrr.appres.EMeasureType;
import etalas.rncrr.model.bean.Points;
import etalas.rncrr.model.bean.Series;
import etalas.rncrr.model.process.api.AbstractSeries;
import etalas.rncrr.model.process.api.IAscFillSeries;

import java.util.Objects;


/**
 * Created by Sidh on 20.02.2015.
 */
public class AscFillSeries extends AbstractSeries implements IAscFillSeries {

    @Override
    public void fillSeries(Series series, String line) {
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

    private void addPointsData(Series series, String line) {
        bIndex = line.indexOf(EAsc.POINT_START.getName()) + 1;
        eIndex = line.indexOf(EAsc.POINT_END.getName());
        str = line.substring(bIndex, eIndex);
        if(Objects.equals(series.getType(), EMeasureType.DDOE.name())
                || Objects.equals(series.getType(), EMeasureType.DDAE.name())
                || Objects.equals(series.getType(), EMeasureType.OPD.name()) )
        {
            fillPoint(series,
                    Double.parseDouble(str.substring(15,21)),
                    Double.parseDouble(str.substring(22)));
        } else if(Objects.equals(series.getType(), EMeasureType.POE.name())
                || Objects.equals(series.getType(), EMeasureType.OPP.name()) )
        {
            fillPoint(series,
                    Double.parseDouble(str.substring(0,6)),
                    Double.parseDouble(str.substring(22)));
        }
    }

    private void fillPoint(Series series, double x, double y){
        points = new Points();
        points.setX(x);
        points.setY(y);
        series.addPoints(points);
    }

}
