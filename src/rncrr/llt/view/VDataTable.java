package rncrr.llt.view;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.SSeries;
import rncrr.llt.model.service.AscFileReader;
import rncrr.llt.view.api.IDataTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import rncrr.llt.view.utils.VUtil;

import java.io.File;

/**
 * Created by Sidh on 23.02.2015.
 */
public class VDataTable implements IDataTable{

    private static final Logger log = LogManager.getLogger(VDataTable.class);
    private ObservableList<SSeries> seriesList;

    public VDataTable() {
        this.seriesList = FXCollections.observableArrayList();
    }

    @Override
    public void viewDataTable(TableView<SSeries> seriesTableView,
                              TableColumn<SSeries, String> columnLabel_1,
                              TableColumn<SSeries, String> columnLabel_2,
                              TableColumn<SSeries, String> columnLabel_3)
    {
        log.debug("Entering into method VDataTable.viewDataTable");
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            log.info(new StringBuilder("Beginning to read file ").append(file).toString());
            try {
                readFile(file);
                columnLabel_1.setCellValueFactory(new PropertyValueFactory<>("type"));
                columnLabel_2.setCellValueFactory(new PropertyValueFactory<>("machineName"));
                columnLabel_3.setCellValueFactory(new PropertyValueFactory<>("beamEnergy"));
                seriesTableView.setItems(seriesList);
            } catch (Exception e) {
                VUtil.alertException("An error occurred while reading the file", e);
                log.error("There was an error in the method VDataTable.viewDataTable", e);
            }
        }
        log.debug("Exit from method VDataTable.viewDataTable");
    }

    @Override
    public void deleteRows(ObservableList<SSeries> selectedList) {
        this.seriesList.removeAll(selectedList);
    }

    private ObservableList<SSeries> readFile(File file){
        AscFileReader fr = new AscFileReader();
        fr.setSeriesList(seriesList);
        return fr.read(file.getPath());
    }
}
