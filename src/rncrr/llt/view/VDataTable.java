package rncrr.llt.view;

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
    public ObservableList<SSeries> viewDataTable(TableView<SSeries> seriesTableView,
                                                 TableColumn<SSeries, String> columnLabel_1,
                                                 TableColumn<SSeries, String> columnLabel_2,
                                                 TableColumn<SSeries, String> columnLabel_3)
    {
        log.trace("Entering into method");
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            readFile(file);
            setColumnLabel(seriesTableView, columnLabel_1, columnLabel_2, columnLabel_3);
        }
        return seriesList;
    }

    @Override
    public void deleteRows(ObservableList<SSeries> selectedList) {
        log.trace("Entering into method");
        try{
            this.seriesList.removeAll(selectedList);
        } catch (Exception e){
            log.error("An error occurred in the method VDataTable.deleteRows", e);
            VUtil.alertException("An error occurred while delete rows", e);
        }
    }

    private ObservableList<SSeries> readFile(File file){
        try{
            AscFileReader fr = new AscFileReader();
            fr.setSeriesList(seriesList);
            return fr.read(file.getPath());
        } catch (Exception e) {
            log.error("An error occurred in the method VDataTable.readFile", e);
            VUtil.alertException("An error occurred while reading the file", e);
        }
        return null;
    }

    private void setColumnLabel(TableView<SSeries> seriesTableView,
                                TableColumn<SSeries, String> columnLabel_1,
                                TableColumn<SSeries, String> columnLabel_2,
                                TableColumn<SSeries, String> columnLabel_3)
    {
        try{
            columnLabel_1.setCellValueFactory(new PropertyValueFactory<>("type"));
            columnLabel_2.setCellValueFactory(new PropertyValueFactory<>("machineName"));
            columnLabel_3.setCellValueFactory(new PropertyValueFactory<>("beamEnergy"));
            seriesTableView.setItems(seriesList);
        } catch (Exception e) {
            log.error("An error occurred in the method VDataTable.setColumnLabel", e);
            VUtil.alertException("An error occurred while set columns value", e);
        }
    }
}
