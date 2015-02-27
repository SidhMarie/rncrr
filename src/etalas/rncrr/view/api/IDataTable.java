package etalas.rncrr.view.api;

import etalas.rncrr.model.bean.Series;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Sidh on 23.02.2015.
 */
public interface IDataTable {

    public void veiwDataTable(TableView<Series> seriesTableView,
                              TableColumn<Series, String> scanIdColumn,
                              TableColumn<Series, String> machineNameColumn,
                              TableColumn<Series, String> energyColumn);
}
