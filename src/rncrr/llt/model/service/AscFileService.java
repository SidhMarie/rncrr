package rncrr.llt.model.service;

import rncrr.llt.model.utils.eobject.EAscFile;
import rncrr.llt.model.utils.eobject.EMeasureType;
import rncrr.llt.model.bean.Points;
import rncrr.llt.model.bean.AscSourceSeries;
import rncrr.llt.model.bean.api.AbstractDataFile;
import rncrr.llt.view.utils.VUtil;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by Sidh on 27.02.2015.
 */
public class AscFileService extends AbstractDataFile {

    private int flag;
    private AscSourceSeries series;

    /**
     * Constructor - initializes the object type SSeries
     */
    public AscFileService() {
        this.flag = 0;
        this.series = new AscSourceSeries();
        this.dataList = new ArrayList<>();
    }

    /**
     * Method looks for the start and end of the frame data and sets the flag
     */
    @Override
    protected void readList() {
        if(dataList.size() == 0) {
            VUtil.alertWarning("File is empty");
            return;
        }
        for(String line : dataList) {
            if (!line.trim().isEmpty()) {
                if (line.equals(EAscFile.BLOCK_START.getName())) {
                    flag = 1;
                } else if (line.equals(EAscFile.BLOCK_END.getName())) {
                    flag = 2;
                } else if (line.equals(EAscFile.FILE_END.getName())) {
                    flag = 0;
                }
                fill(line);
            }
        }
    }

    /**
     * Method fills the series and adds it to the collection
     * @param line - input line
     */
    private void fill(String line) {
        switch (flag) {
            case 1 :
                fillSeries(line);
                break;
            case 2 :
                seriesList.add(series);
                series = new AscSourceSeries();
                break;
        }
    }

    /**
     * Method reads the incoming line and adds value in the series
     * @param line - input line
     */
    private void fillSeries(String line) {
        for(EAscFile value : EAscFile.values()) {
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
                        addPointsData(line);
                        break;
                }
            }
        }
    }

    /**
     * Method parses a line with the data coordinates and adds them to the object type SSeries
     * @param line - input line
     */
    private void addPointsData(String line) {
        String serviceString = line.substring(1, 28);
        if(Objects.equals(series.getType(), EMeasureType.DDOE.name())
                || Objects.equals(series.getType(), EMeasureType.DDAE.name())
                || Objects.equals(series.getType(), EMeasureType.OPD.name()) )
        {
            fillPoint(Double.parseDouble(serviceString.substring(15, 21)),
                      Double.parseDouble(serviceString.substring(22)));
        } else if(Objects.equals(series.getType(), EMeasureType.POE.name())
                || Objects.equals(series.getType(), EMeasureType.OPP.name()) )
        {
            fillPoint(Double.parseDouble(serviceString.substring(0, 6)),
                      Double.parseDouble(serviceString.substring(22)));
        }
    }

    /**
     * Method adds an object type Points in the series.
     * @param x - coordinate
     * @param y - coordinate
     */
    private void fillPoint(double x, double y){
        series.addPoints(new Points(x,y));
    }

}
