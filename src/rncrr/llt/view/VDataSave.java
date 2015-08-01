package rncrr.llt.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.service.AscFileService;
import rncrr.llt.view.api.IDataSave;
import rncrr.llt.view.api.IDataTable;
import rncrr.llt.view.utils.VUtil;


/**
 * Created by Sidh on 28.07.2015.
 */
public class VDataSave implements IDataSave {

    private static final Logger log = LogManager.getLogger(VDataSave.class);

    @Override
    public void dataSave(IDataTable dataTable) throws Exception {
        log.trace("");
        AscFileService fileService = dataTable.getFileService();
        if(fileService.dataLine.size() != 0){

        } else {
            log.warn("An error occurred in the method View.saveFileData -> No data for saving");
            VUtil.alertWarning("No data for saving");
        }
    }

    private void write(){

    }
}
