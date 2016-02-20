package rncrr.llt.view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import rncrr.llt.model.bean.AscSourceSeries;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.service.AscFileService;
import rncrr.llt.view.api.IAscTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.File;

/**
 * Created by Sidh on 23.02.2015.
 * This class is designed for reading the input file
 * Working with a collection of series - fills, removes the selected items
 */
public class VAscTable implements IAscTable {

    private AscFileService fileService;
    private ObservableList<ISourceSeries> seriesList;

    /**
     * The class constructor VDataTable
     * Initializes a new collection of series
     */
    public VAscTable() {
        this.fileService = new AscFileService();
        this.seriesList = FXCollections.observableArrayList();
    }

    /**
     *
     * @return
     */
    public AscFileService getFileService() {
        return fileService;
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
            readFile(file);
            columnLabel_1.setCellValueFactory(new PropertyValueFactory<>("type"));
            columnLabel_2.setCellValueFactory(new PropertyValueFactory<>("machineName"));
            columnLabel_3.setCellValueFactory(new PropertyValueFactory<>("beamEnergy"));
            seriesTableView.setItems(seriesList);
        }
        return seriesList;
    }

    /**
     * The method deletes rows from table
     * @param selectedList - object type ObservableList
     */
    @Override
    public void deleteRows(ObservableList<ISourceSeries> selectedList) throws Exception {
        this.seriesList.removeAll(selectedList);
    }

    private ObservableList<ISourceSeries> readFile(File file) throws Exception {
        fileService.setSeriesList(seriesList);
        return fileService.read(file.getPath());
    }

}
