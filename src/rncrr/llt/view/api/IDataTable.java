package rncrr.llt.view.api;

import rncrr.llt.model.bean.SSeries;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Sidh on 23.02.2015.
 */
public interface IDataTable {

    ObservableList<SSeries> viewDataTable(TableView<SSeries> seriesTableView) throws Exception;

    void deleteRows(ObservableList<SSeries> selectedList) throws Exception;

}
