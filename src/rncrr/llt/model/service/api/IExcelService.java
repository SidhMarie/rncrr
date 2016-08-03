package rncrr.llt.model.service.api;

import rncrr.llt.model.bean.ExportData;

import java.util.List;

/**
 * Created by Sidh on 22.04.2016.
 */
public interface IExcelService {

    void createSheet(String nameSheet, List<ExportData> dataList);
}
