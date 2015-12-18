package rncrr.llt.view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.service.AscFileService;
import rncrr.llt.view.api.IDataTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by Sidh on 23.02.2015.
 * This class is designed for reading the input file
 * Working with a collection of series - fills, removes the selected items
 */
public class VDataTable implements IDataTable{

    private AscFileService fileService;
    private ObservableList<SourceSeries> seriesList;
    private static final Logger log = LogManager.getLogger(VDataTable.class);


    /**
     * The class constructor VDataTable
     * Initializes a new collection of series
     */
    public VDataTable() {
        log.trace("Entering into class VDataTable");
        log.trace("Try to initialize new collection seriesList");
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
    public ObservableList<SourceSeries> viewDataTable(File file,
                                                 TableView<SourceSeries> seriesTableView,
                                                 TableColumn<SourceSeries, String> columnLabel_1,
                                                 TableColumn<SourceSeries, String> columnLabel_2,
                                                 TableColumn<SourceSeries, String> columnLabel_3) throws Exception {
        log.trace("Entering into method -> VDataTable.viewDataTable");
        if(file != null) {
            log.trace("Try to read the selected file");
            readFile(file);
            log.trace("Set the data of series");
            //todo - improved based on the properties file
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
    public void deleteRows(ObservableList<SourceSeries> selectedList) throws Exception {
        log.trace("Entering into method -> VDataTable.deleteRows");
        log.trace("Try to remove the selected rows from the list of series ");
        this.seriesList.removeAll(selectedList);
    }

    private ObservableList<SourceSeries> readFile(File file) throws Exception {
        log.trace("Entering into method -> VDataTable.readFile");
        log.trace("Try to create new object AscFileReader");

        log.trace("Set collection seriesList");
        fileService.setSeriesList(seriesList);
        log.trace("Try to read file data and return it");
        return fileService.read(file.getPath());
    }

}
