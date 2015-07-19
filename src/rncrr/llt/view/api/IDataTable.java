package rncrr.llt.view.api;

import rncrr.llt.model.bean.SourceSeries;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Sidh on 23.02.2015.
 */
public interface IDataTable {

    ObservableList<SourceSeries> viewDataTable(TableView<SourceSeries> seriesTableView,
                                          TableColumn<SourceSeries, String> columnLabel_1,
                                          TableColumn<SourceSeries, String> columnLabel_2,
                                          TableColumn<SourceSeries, String> columnLabel_3) throws Exception;

    void deleteRows(ObservableList<SourceSeries> selectedList) throws Exception;

}
