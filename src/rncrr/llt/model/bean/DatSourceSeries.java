package rncrr.llt.model.bean;

import rncrr.llt.model.bean.api.AbstractSourceSeries;
import java.util.ArrayList;


/**
 * Created by Sidh on 15.02.2016.
 */
public class DatSourceSeries extends AbstractSourceSeries {

    private String dataType;
    private String seriesName;

    public DatSourceSeries() {
        points = new ArrayList<>();
    }

    public String getDataType() { return dataType; }

    public void setDataType(String dataType) { this.dataType = dataType; }

    public String getSeriesName() { return seriesName; }

    public void setSeriesName(String seriesName) { this.seriesName = seriesName; }
}
