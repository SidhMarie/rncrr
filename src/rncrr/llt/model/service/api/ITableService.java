package rncrr.llt.model.service.api;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import rncrr.llt.model.bean.api.ISourceSeries;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Sidh
 * Date: 25.07.16
 * Time: 18:03
 * To change this template use File | Settings | File Templates.
 */
public interface ITableService {

    public ObservableList<ISourceSeries> viewDataTable(File file,
                                                       TableView<ISourceSeries> seriesDatTableView,
                                                       TableColumn<ISourceSeries, String> columnLabelDat_1,
                                                       TableColumn<ISourceSeries, String> columnLabelDat_2) throws Exception;

    public ObservableList<ISourceSeries> viewDataTable(File file,
                                                       TableView<ISourceSeries> seriesTableView,
                                                       TableColumn<ISourceSeries, String> columnLabel_1,
                                                       TableColumn<ISourceSeries, String> columnLabel_2,
                                                       TableColumn<ISourceSeries, String> columnLabel_3) throws Exception;

    public void deleteRows(TableView<ISourceSeries> seriesTableView) throws Exception;
}
