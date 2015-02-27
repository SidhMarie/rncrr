package etalas.rncrr.model.process;

import etalas.rncrr.model.bean.Points;
import etalas.rncrr.model.bean.Series;
import etalas.rncrr.model.process.api.AbstractSeries;
import etalas.rncrr.model.process.api.IFillSeries;


/**
 * Created by Sidh on 20.02.2015.
 */
public class FillSeries extends AbstractSeries implements IFillSeries {

    public FillSeries() {}

    @Override
    public void fillSeries(Series series, String line) {
        for(ESeries value : ESeries.values()){
            if(line.contains(value.getName())){
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
                        break;
                    case BLOCK_DATE :
                        series.setDate(getValue(line, value.getName()));
                        break;
                    case BLOCK_BMTY :
                        series.setBeamType(getValue(line, value.getName()));
                        break;
                    case BLOCK_STEP :
                        series.setStep(Integer.parseInt(getValue(line, value.getName())));
                        break;
                    case BLOCK_SSD :
                        series.setSsd(Integer.parseInt(getValue(line, value.getName())));
                        break;
                    case BLOCK_FLSZ :
                        series.setFieldSize(getValue(line, value.getName()));
                        break;
                    case BLOCK_DPTH :
                        series.setDepth(Integer.parseInt(getValue(line, value.getName())));
                        break;
                    case BLOCK_AXIS :
                        series.setAxis(getValue(line, value.getName()));
                        break;
                    case POINT_START :
                        addPointsData(series, line);
                        break;
                }
            }
        }
    }

    private void addPointsData(Series series, String line) {
        bIndex = line.indexOf(ESeries.POINT_START.getName()) + 1;
        eIndex = line.indexOf(ESeries.POINT_END.getName());
        str = line.substring(bIndex, eIndex);
        if(series.getAxis().equalsIgnoreCase("X")){
            x = Double.parseDouble(str.substring(0,6));
            y = Double.parseDouble(str.substring(22));
        } else if(series.getAxis().equalsIgnoreCase("Y")) {

        } else if(series.getAxis().equalsIgnoreCase("Z")){
            x = Double.parseDouble(str.substring(15,21));
            y = Double.parseDouble(str.substring(22));
        }
        fillPoint(series, x, y);
    }

    private void fillPoint(Series series, double x, double y){
        points = new Points();
        points.setX(x);
        points.setY(y);
        series.addPoints(points);
    }

}
