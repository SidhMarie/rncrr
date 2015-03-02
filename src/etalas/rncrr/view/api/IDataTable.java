package etalas.rncrr.view.api;

import etalas.rncrr.model.bean.Series;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Sidh on 23.02.2015.
 */
public interface IDataTable {

    public void viewDataTable(TableView<Series> seriesTableView,
                              TableColumn<Series, String> columnLabel_1,
                              TableColumn<Series, String> columnLabel_2,
                              TableColumn<Series, String> columnLabel_3);

    public void deleteRows(ObservableList<Series> selectedList);

}
