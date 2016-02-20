package rncrr.llt.view.api;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import rncrr.llt.model.bean.api.ISourceSeries;

import java.io.File;

/**
 * Created by Sidh on 29.01.2016.
 */
public interface IDatTable {

    ObservableList<ISourceSeries> viewDataTable(File file, TableView<ISourceSeries> seriesDatTableView,
                                                TableColumn<ISourceSeries, String> columnDatLabel_1,
                                                TableColumn<ISourceSeries, String> columnDatLabel_2) throws Exception;

    void deleteRows(ObservableList<ISourceSeries> selectedList) throws Exception;
}
