package rncrr.llt.model.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.service.api.AbstractDataFile;
import rncrr.llt.model.service.api.ITableService;
import rncrr.llt.model.service.utils.AlertService;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Sidh
 * Date: 25.07.16
 * Time: 18:02
 * To change this template use File | Settings | File Templates.
 */
public class TableService implements ITableService {

    private ObservableList<ISourceSeries> seriesList;

    public TableService() {}

    @Override
    public ObservableList<ISourceSeries> viewDataTable(File file,
                                                       TableView<ISourceSeries> seriesDatTableView,
                                                       TableColumn<ISourceSeries, String> columnLabelDat_1,
                                                       TableColumn<ISourceSeries, String> columnLabelDat_2) throws Exception {
        if(file != null){
            this.seriesList = FXCollections.observableArrayList();
            readFile(new DatFileService(), file);
            columnLabelDat_1.setCellValueFactory(new PropertyValueFactory<>("dataType"));
            columnLabelDat_2.setCellValueFactory(new PropertyValueFactory<>("seriesName"));
            seriesDatTableView.setItems(seriesList);
        }
        return seriesList;
    }

    /**
     * The method opens the file and populates the collection of data
     * @param seriesTableView - object type TableView
     * @return seriesList - object type ObservableList
     */
    @Override
    public ObservableList<ISourceSeries> viewDataTable(File file,
                                                       TableView<ISourceSeries> seriesTableView,
                                                       TableColumn<ISourceSeries, String> columnLabel_1,
                                                       TableColumn<ISourceSeries, String> columnLabel_2,
                                                       TableColumn<ISourceSeries, String> columnLabel_3) throws Exception {
        if(file != null) {
            this.seriesList = FXCollections.observableArrayList();
            readFile(new AscFileService(), file);
            columnLabel_1.setCellValueFactory(new PropertyValueFactory<>("type"));
            columnLabel_2.setCellValueFactory(new PropertyValueFactory<>("machineName"));
            columnLabel_3.setCellValueFactory(new PropertyValueFactory<>("beamEnergy"));
            seriesTableView.setItems(seriesList);
        }
        return seriesList;
    }

    @Override
    public void deleteRows(TableView<ISourceSeries> seriesTableView) throws Exception {
        ObservableList<ISourceSeries> selectedList = seriesTableView.getSelectionModel().getSelectedItems();
        if(!selectedList.isEmpty()) {
            this.seriesList.removeAll(selectedList);
        } else {
            AlertService.alertMessage("You must select a row to remove");
        }
    }

    private ObservableList<ISourceSeries> readFile(AbstractDataFile fileService, File file) throws Exception {
        fileService.setSeriesList(seriesList);
        return fileService.read(file.getPath());
    }


}
