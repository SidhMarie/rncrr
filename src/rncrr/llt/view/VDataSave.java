package rncrr.llt.view;

import rncrr.llt.model.service.AscFileService;
import rncrr.llt.view.api.IDataSave;
import rncrr.llt.view.api.IAscTable;
import rncrr.llt.view.utils.VUtil;


/**
 * Created by Sidh on 28.07.2015.
 */
public class VDataSave implements IDataSave {

    @Override
    public void dataSave(IAscTable dataTable) throws Exception {
        AscFileService fileService = dataTable.getFileService();
        if(fileService.dataList.size() != 0) {

        } else {
            VUtil.printWarning("An error occurred in the method View.saveFileData -> No data for saving");
            VUtil.alertWarning("No data for saving");
        }
    }

    private void write(){

    }
}
