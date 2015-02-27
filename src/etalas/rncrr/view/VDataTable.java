package etalas.rncrr.view;

import etalas.rncrr.model.bean.Series;
import etalas.rncrr.model.service.FileReader;
import etalas.rncrr.view.api.IDataTable;
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

    @Override
    public void veiwDataTable(TableView<Series> seriesTableView,
                              TableColumn<Series, String> scanIdColumn,
                              TableColumn<Series, String> machineNameColumn,
                              TableColumn<Series, String> energyColumn)
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            try {
                seriesList = new FileReader().read(file.getPath());
                scanIdColumn.setCellValueFactory(new PropertyValueFactory<>("scanId"));
                machineNameColumn.setCellValueFactory(new PropertyValueFactory<>("machineName"));
                energyColumn.setCellValueFactory(new PropertyValueFactory<>("beamEnergy"));
                seriesTableView.setItems(seriesList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
