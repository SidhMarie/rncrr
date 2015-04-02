package rncrr.llt.view.api;

import rncrr.llt.model.bean.SSeries;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Sidh on 23.02.2015.
 */
public interface IDataTable {

    public ObservableList<SSeries> viewDataTable(TableView<SSeries> seriesTableView,
                                                 TableColumn<SSeries, String> columnLabel_1,
                                                 TableColumn<SSeries, String> columnLabel_2,
                                                 TableColumn<SSeries, String> columnLabel_3);

    public void deleteRows(ObservableList<SSeries> selectedList);

}
