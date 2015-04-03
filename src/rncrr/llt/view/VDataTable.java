package rncrr.llt.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.SSeries;
import rncrr.llt.model.service.AscFileReader;
import rncrr.llt.view.api.IDataTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import rncrr.llt.view.utils.VUtil;
import java.io.File;

/**
 * Created by Sidh on 23.02.2015.
 * Класс предназначен для чтения входящего файла
 * Работает с коллекцией серий - заполняет, удаляет выбранные элементы
 */
public class VDataTable implements IDataTable{

    private ObservableList<SSeries> seriesList;
    private static final Logger log = LogManager.getLogger(VDataTable.class);


    /**
     * Конструктор класса VDataTable
     * Инициализирует новыю коллекцию серий
     */
    public VDataTable() {
        log.trace("Entering into class VDataTable");
        log.trace("Try to initialize new collection seriesList");
        this.seriesList = FXCollections.observableArrayList();
    }

    /**
     * Метод открывает FileChooser
     * @param seriesTableView
     * @return seriesList
     */
    @Override
    public ObservableList<SSeries> viewDataTable(TableView<SSeries> seriesTableView) {
        log.trace("Entering into method -> VDataTable.viewDataTable");
        log.trace("Try to open FileChooser");
        FileChooser fileChooser = new FileChooser();
        log.trace("Select the file in the FileChooser");
        File file = fileChooser.showOpenDialog(null);
        if(file != null) {
            log.trace("Try to read the selected file");
            readFile(file);
            log.trace("Set the data of series");
            seriesTableView.setItems(seriesList);
        } else {
            log.trace("Collection series is not generated, because the data file is not selected or null", new NullPointerException());
            VUtil.alertWarning("Collection series is not generated, because the data file is not selected or null");
        }
        log.trace("Try to return the completed collection of series or NULL");
        return seriesList;
    }

    /**
     *
     * @param selectedList
     */
    @Override
    public void deleteRows(ObservableList<SSeries> selectedList) {
        log.trace("Entering into method -> VDataTable.deleteRows");
        try{
            log.trace("Try to remove the selected rows from the list of series ");
            this.seriesList.removeAll(selectedList);
        } catch (Exception e){
            log.error("An error occurred in the method VDataTable.deleteRows", e);
            VUtil.alertException("An error occurred while delete rows", e);
        }
    }

    /**
     *
     * @param file
     * @return
     */
    private ObservableList<SSeries> readFile(File file) {
        log.trace("Entering into method -> VDataTable.readFile");
        try{
            log.trace("Try to create new object AscFileReader");
            AscFileReader fr = new AscFileReader();
            log.trace("Set collection seriesList");
            fr.setSeriesList(seriesList);
            log.trace("Try to read file data");
            return fr.read(file.getPath());
        } catch (Exception e) {
            log.error("An error occurred in the method VDataTable.readFile", e);
            VUtil.alertException("An error occurred while reading the file", e);
        }
        return null;
    }

}
