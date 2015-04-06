package rncrr.llt.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.utils.eobject.EMeasureType;
import rncrr.llt.model.bean.SSeries;
import rncrr.llt.model.utils.Config;
import rncrr.llt.view.api.IDataChart;
import rncrr.llt.view.api.IDataTable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import rncrr.llt.view.utils.VUtil;

import java.util.Objects;


public class View {

    private static final Logger log = LogManager.getLogger(View.class);

    private IDataTable dataTable;
    private IDataChart dataChart;

    /**
     * Конструктор - инициализирует объекты VDataTable и VDataChart
     */
    public View() {
        log.trace("Entering into class -> View");
        log.trace("Initialize the new object -> VDataTable");
        dataTable = new VDataTable();
        log.trace("Initialize the new object -> VDataChart");
        dataChart = new VDataChart();
    }

    /**
     * Метод открывает на чтение файл с данными
     * Инициализирует область графика
     * Очищает таблицу подробного представления данных
     */
    public void openFileData(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.openFileData");
        try{
            log.trace("Try to open a data file for reading");
            dataTable.viewDataTable(seriesTableView);
            log.trace("Try to initialize chart");
            dataChart.initChart(profileChart);
            log.trace("Try to clear the table details");
            clearDataGridValue();
        } catch (Exception e) {
            log.error("An error occurred in the method View.openFileData", e);
            VUtil.alertException("An error occurred while trying to open and read the file", e);
        }
    }

    /**
     * Метод заполняет таблицу детальной информации серии
     * и строит график по данным серии
     */
    public void detailRows(Event event) {
        log.trace("Entering into method -> View.detailRows");
        if (!seriesTableView.getItems().isEmpty()) {
            SSeries series = seriesTableView.getSelectionModel().getSelectedItem();
            log.trace("Try to set the values in the table details");
            setAscDataGridValue(series);
            log.trace("Try to build a chart");
            dataChart.buildingChart(seriesTableView, profileChart);
        }
    }

    /**
     * Метод удаляет выбранную строку в списке серий
     * Очищает график и таблицу детальной инормации
     */
    public void deleteRows(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.deleteRows");
        try{
            if (!seriesTableView.getItems().isEmpty()) {
                log.trace("Try to remove the selected row");
                dataTable.deleteRows(seriesTableView.getSelectionModel().getSelectedItems());
                log.trace("Try to clear the chart");
                dataChart.clearChart(profileChart);
                log.trace("Try to clear the table details");
                clearDataGridValue();
            }
        } catch (Exception e){
            log.error("An error occurred in the method View.deleteRows", e);
            VUtil.alertException("An error occurred while deleting a row", e);
        }
    }

    /**
     * Метод завершает выполнение приложения
     */
    public void closeApplication(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.closeApplication");
        log.trace("Try to close the application. System.exit");
        System.exit(0);
    }

    /**
     * Метод устанавливает значения полей таблицы подробной информации
     * в зависимости от типа измерений.
     *
     * @param series - объект типа SSeries
     */
    private void setAscDataGridValue(SSeries series) {
        log.trace("Entering into method -> View.setAscDataGridValue");
        if (Objects.equals(series.getType(), EMeasureType.OPP.name())
                || Objects.equals(series.getType(), EMeasureType.OPD.name())) {
            log.trace("Set the text and value for OPP/OPD types");
            setValues4OPPDataGrid(series);
        } else if (Objects.equals(series.getType(), EMeasureType.DDOE.name())
                || Objects.equals(series.getType(), EMeasureType.DDAE.name())
                || Objects.equals(series.getType(), EMeasureType.POE.name())) {
            log.trace("Set the text and value for DDOE/DDAE/POE types");
            setValues4DDOEDataGrid(series);
        }
    }

    /**
     * Метод устанавливает значения полей таблицы подробной информации
     * для типа OPP и OPD
     *
     * @param series - объект типа SSeries
     */
    private void setValues4OPPDataGrid(SSeries series) {
        rowLabel_0.setText(Config.getStringProperty("tg.row.label.scanId", "Scan ID"));
        rowLabel_1.setText(Config.getStringProperty("tg.row.label.machine", "Machine name"));
        rowLabel_2.setText(Config.getStringProperty("tg.row.label.beamType", "Beam type"));
        rowLabel_3.setText(Config.getStringProperty("tg.row.label.pnts", "PNTS"));
        rowLabel_4.setText(Config.getStringProperty("tg.row.label.ssd", "Ssd"));
        rowLabel_5.setText(Config.getStringProperty("tg.row.label.fieldSize", "Field size"));
        rowLabel_6.setText(Config.getStringProperty("tg.row.label.axis", "Axis"));
        rowLabel_7.setText(Config.getStringProperty("tg.row.label.depth", "Depth"));
        rowLabel_8.setText(Config.getStringProperty("tg.row.label.step", "Step"));
        rowLabel_9.setText(Config.getStringProperty("tg.row.label.date", "Date"));

        rowValue_0.setText(series.getScanId());
        rowValue_1.setText(series.getMachineName());
        rowValue_2.setText(series.getBeamType() + " " + series.getBeamEnergy());
        rowValue_3.setText(Integer.toString(series.getPoints().size()));
        rowValue_4.setText(Integer.toString(series.getSsd()) + " mm");
        rowValue_5.setText(series.getFieldSize());
        rowValue_6.setText(series.getAxis());
        rowValue_7.setText(Integer.toString(series.getDepth()) + " mm");
        rowValue_8.setText(Integer.toString(series.getStep()) + " mm");
        rowValue_9.setText(series.getDate());
    }

    /**
     * Метод устанавливает значения полей таблицы подробной информации
     * для типа DDOE, DDAE и POE
     *
     * @param series - объект типа SSeries
     */
    private void setValues4DDOEDataGrid(SSeries series) {
        rowLabel_0.setText(Config.getStringProperty("tg.row.label.scanId", "Scan ID"));
        rowLabel_1.setText(Config.getStringProperty("tg.row.label.machine", "Machine name"));
        rowLabel_2.setText(Config.getStringProperty("tg.row.label.beamType", "Beam type"));
        rowLabel_3.setText(Config.getStringProperty("tg.row.label.beamEnergy", "Beam energy"));
        rowLabel_4.setText(Config.getStringProperty("tg.row.label.spd", "SPD"));
        rowLabel_5.setText(Config.getStringProperty("tg.row.label.pnts", "PNTS"));
        rowLabel_6.setText(Config.getStringProperty("tg.row.label.cdepth", "Calb. Depth"));
        rowLabel_7.setText(Config.getStringProperty("tg.row.label.cfactor", "Calb. Factor"));
        rowLabel_8.setText(Config.getStringProperty("tg.row.label.fieldSize", "Field size"));
        rowLabel_9.setText(Config.getStringProperty("tg.row.label.date", "Date"));


        rowValue_0.setText(series.getScanId());
        rowValue_1.setText(series.getMachineName());
        rowValue_2.setText(series.getBeamType());
        rowValue_3.setText(series.getBeamEnergy());
        rowValue_4.setText(Double.toString(series.getSpd()));
        rowValue_5.setText(Integer.toString(series.getPnts()));
        rowValue_6.setText(Double.toString(series.getcDepth()));
        rowValue_7.setText(Double.toString(series.getcFactor()));
        rowValue_8.setText(series.getFieldSize());
        rowValue_9.setText(series.getDate());
    }

    /**
     * Метод очищает поля таблицы подробной информации
     */
    private void clearDataGridValue() {
        rowLabel_0.setText("");
        rowLabel_1.setText("");
        rowLabel_2.setText("");
        rowLabel_3.setText("");
        rowLabel_4.setText("");
        rowLabel_5.setText("");
        rowLabel_6.setText("");
        rowLabel_7.setText("");
        rowLabel_8.setText("");
        rowLabel_9.setText("");
        rowValue_0.setText("");
        rowValue_1.setText("");
        rowValue_2.setText("");
        rowValue_3.setText("");
        rowValue_4.setText("");
        rowValue_5.setText("");
        rowValue_6.setText("");
        rowValue_7.setText("");
        rowValue_8.setText("");
        rowValue_9.setText("");
    }

    @FXML
    private TableView<SSeries> seriesTableView;
    @FXML
    private LineChart<Double, Double> profileChart;
    @FXML
    private Label rowLabel_0;
    @FXML
    private Label rowLabel_1;
    @FXML
    private Label rowLabel_2;
    @FXML
    private Label rowLabel_3;
    @FXML
    private Label rowLabel_4;
    @FXML
    private Label rowLabel_5;
    @FXML
    private Label rowLabel_6;
    @FXML
    private Label rowLabel_7;
    @FXML
    private Label rowLabel_8;
    @FXML
    private Label rowLabel_9;
    @FXML
    private Label rowValue_0;
    @FXML
    private Label rowValue_1;
    @FXML
    private Label rowValue_2;
    @FXML
    private Label rowValue_3;
    @FXML
    private Label rowValue_4;
    @FXML
    private Label rowValue_5;
    @FXML
    private Label rowValue_6;
    @FXML
    private Label rowValue_7;
    @FXML
    private Label rowValue_8;
    @FXML
    private Label rowValue_9;

}
