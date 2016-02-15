package rncrr.llt.view.api;

import rncrr.llt.model.bean.AscSourceSeries;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import rncrr.llt.model.service.AscFileService;

import java.io.File;

/**
 * Created by Sidh on 23.02.2015.
 */
public interface IAscTable {

    AscFileService getFileService();

    ObservableList<AscSourceSeries> viewDataTable(File file, TableView<AscSourceSeries> seriesTableView,
                                          TableColumn<AscSourceSeries, String> columnLabel_1,
                                          TableColumn<AscSourceSeries, String> columnLabel_2,
                                          TableColumn<AscSourceSeries, String> columnLabel_3) throws Exception;

    void deleteRows(ObservableList<AscSourceSeries> selectedList) throws Exception;

}
