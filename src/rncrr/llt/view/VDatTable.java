package rncrr.llt.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import rncrr.llt.model.bean.DatSourceSeries;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.service.DatFileService;
import rncrr.llt.model.utils.eobject.EDatFileValue;
import rncrr.llt.view.api.IDatTable;

import java.io.File;

/**
 * Created by Sidh on 29.01.2016.
 */
public class VDatTable implements IDatTable {

    private DatFileService fileService;
    private ObservableList<ISourceSeries> seriesList;

    public VDatTable(){
        this.fileService = new DatFileService();
        this.seriesList = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<ISourceSeries> viewDataTable(File file, TableView<ISourceSeries> seriesDatTableView,
                                                       TableColumn<ISourceSeries, String> columnLabelDat_1,
                                                       TableColumn<ISourceSeries, String> columnLabelDat_2) throws Exception {
        if(file != null){
            readFile(file);
            columnLabelDat_1.setCellValueFactory(new PropertyValueFactory<>("dataType"));
            columnLabelDat_2.setCellValueFactory(new PropertyValueFactory<>("seriesName"));
            seriesDatTableView.setItems(seriesList);
        }
        return seriesList;
    }

    @Override
    public void deleteRows(ObservableList<ISourceSeries> selectedList) throws Exception {
        this.seriesList.removeAll(selectedList);
    }

    private ObservableList<ISourceSeries> readFile(File file) throws Exception {
        fileService.setSeriesList(seriesList);
        seriesList = fileService.read(file.getPath());


        return seriesList;
    }
}
