package rncrr.llt.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.SSeries;
import rncrr.llt.model.service.AscFileReaderService;
import rncrr.llt.view.api.IDataTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

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
    public ObservableList<SSeries> viewDataTable(TableView<SSeries> seriesTableView) throws Exception {
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
        }
        return seriesList;
    }

    /**
     *
     * @param selectedList
     */
    @Override
    public void deleteRows(ObservableList<SSeries> selectedList) throws Exception {
        log.trace("Entering into method -> VDataTable.deleteRows");
        log.trace("Try to remove the selected rows from the list of series ");
        this.seriesList.removeAll(selectedList);
    }

    /**
     *
     * @param file
     * @return
     */
    private ObservableList<SSeries> readFile(File file) throws Exception {
        log.trace("Entering into method -> VDataTable.readFile");
        log.trace("Try to create new object AscFileReader");
        AscFileReaderService fr = new AscFileReaderService();
        log.trace("Set collection seriesList");
        fr.setSeriesList(seriesList);
        log.trace("Try to read file data and return it");
        return fr.read(file.getPath());
    }

}
