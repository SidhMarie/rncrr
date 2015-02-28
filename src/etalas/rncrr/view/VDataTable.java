package etalas.rncrr.view;

import etalas.rncrr.model.bean.Series;
import etalas.rncrr.model.service.AscFileReader;
import etalas.rncrr.view.api.IDataTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by Sidh on 23.02.2015.
 */
public class VDataTable implements IDataTable{

    private ObservableList<Series> seriesList;

    public VDataTable() {
        this.seriesList = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Series> getSeriesList() {
        return seriesList;
    }

    @Override
    public void viewDataTable(TableView<Series> seriesTableView,
                              TableColumn<Series, String> scanIdColumn,
                              TableColumn<Series, String> machineNameColumn,
                              TableColumn<Series, String> energyColumn)
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            try {
                readFile(file);
                scanIdColumn.setCellValueFactory(new PropertyValueFactory<>("scanId"));
                machineNameColumn.setCellValueFactory(new PropertyValueFactory<>("machineName"));
                energyColumn.setCellValueFactory(new PropertyValueFactory<>("beamEnergy"));
                seriesTableView.setItems(seriesList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ObservableList<Series> readFile(File file){
        AscFileReader fr = new AscFileReader();
        fr.setSeriesList(seriesList);
        return fr.read(file.getPath());
    }
}
