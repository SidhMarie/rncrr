package rncrr.llt.view.api;

import rncrr.llt.model.bean.AscSourceSeries;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.service.AscFileService;

import java.io.File;

/**
 * Created by Sidh on 23.02.2015.
 */
public interface IAscTable {

    AscFileService getFileService();

    ObservableList<ISourceSeries> viewDataTable(File file, TableView<ISourceSeries> seriesTableView,
                                          TableColumn<ISourceSeries, String> columnLabel_1,
                                          TableColumn<ISourceSeries, String> columnLabel_2,
                                          TableColumn<ISourceSeries, String> columnLabel_3) throws Exception;

    void deleteRows(ObservableList<ISourceSeries> selectedList) throws Exception;

}
