package rncrr.llt.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.utils.eobject.ECharts;
import rncrr.llt.model.utils.eobject.EMeasureType;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.utils.Config;
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
    private VSourceChart sourceChart;
    private VTransformChart transformChart;

    /**
     * Constructor - initialize objects VDataTable и VDataChart
     */
    public View() {
        log.trace("Entering into class -> View");
        log.trace("Initialize the new object -> VDataTable");
        dataTable = new VDataTable();
        log.trace("Initialize the new object -> VSourceChart");
        sourceChart = new VSourceChart();
        log.trace("Initialize the new object -> VTransformChart");
        transformChart = new VTransformChart();
    }

    /**
     * The method opens for reading a data file
     * Initializes the graphics
     */
    public void openFileData(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.openFileData");
        try{
            log.trace("Try to open a data file for reading");
            dataTable.viewDataTable(seriesTableView, columnLabel_1, columnLabel_2, columnLabel_3);
            log.trace("Try to initialize chart");
            sourceChart.initChart(profileChart);
            transformChart.initChart(spectrumChart);
        } catch (Exception e) {
            log.error("An error occurred in the method View.openFileData", e);
            VUtil.alertException("An error occurred while trying to open and read the file", e);
        }
    }

    public void saveFileData(ActionEvent actionEvent) {
    }

    /**
     * Method fills in the table details the series and plotted according to series
     */
    public void detailSelectedRow(Event event) {
        log.trace("Entering into method -> View.detailSelectedRow");
        try{
            if (!seriesTableView.getItems().isEmpty()) {
                SourceSeries series = seriesTableView.getSelectionModel().getSelectedItem();
                log.trace("Try to set the values in the table details");
                setAscDataGridValue(series);
                log.trace("Try to build profile signal chart");
                sourceChart.buildingSourceChart(seriesTableView, profileChart);
                transformChart.clearChart(spectrumChart);
            }
        } catch (Exception e) {
            log.error("An error occurred in the method View.detailSelectedRow",e);
            VUtil.alertException("An error occurred while trying to view detail data and building chart",e);
        }
    }

    /**
     * The method removes the selected row in the list of series
     * Clears the graph and table detailed use information
     */
    public void deleteRows(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.deleteRows");
        try{
            if (!seriesTableView.getItems().isEmpty()) {
                log.trace("Try to remove the selected row");
                dataTable.deleteRows(seriesTableView.getSelectionModel().getSelectedItems());
                log.trace("Try to clear the charts");
                sourceChart.clearChart(profileChart);
                transformChart.clearChart(spectrumChart);
                log.trace("Try to clear the table details");
                clearDataGridValue();
            } else {
                log.warn("You must select a row to remove");
                VUtil.alertMessage("You must select a row to remove");
            }
        } catch (Exception e){
            log.error("An error occurred in the method View.deleteRows", e);
            VUtil.alertException("An error occurred while deleting a row", e);
        }
    }

    /**
     * Method completes the execution of the application
     */
    public void closeApplication(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.closeApplication");
        log.trace("Try to close the application. System.exit");
        System.exit(0);
    }


    /**
     *
     */
    public void windowData(ActionEvent actionEvent) {
        try{
            transformChart.buildingTChart(seriesTableView, spectrumChart, ECharts.WINDOW, windowData);
        } catch (Exception e){
            log.error("An error occurred in the method View.transformData", e);
            VUtil.alertException("An error occurred while transformation data", e);
        }
    }

    /**
     *
     */
    public void transformData(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.transformData");
        try {
            log.trace("Try to build spectrum signal chart");
            transformChart.buildingTChart(seriesTableView, spectrumChart, ECharts.SPECTRUM, windowData);
        } catch (Exception e) {
            log.error("An error occurred in the method View.transformData", e);
            VUtil.alertException("An error occurred while transformation data", e);
        }
    }

    /**
     * The method sets the information fields of the table depending on the type of measurement.
     * @param series - object type SSeries
     */
    private void setAscDataGridValue(SourceSeries series) {
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
     * Method sets the values of the fields of the table details for type OPP и OPD
     * @param series - object type SSeries
     */
    private void setValues4OPPDataGrid(SourceSeries series) {
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
     * Method sets the values of the fields of the table details for type DDOE, DDAE и POE
     * @param series - object type SSeries
     */
    private void setValues4DDOEDataGrid(SourceSeries series) {
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
     * Method clears the table field detailed information
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
    private TableView<SourceSeries> seriesTableView;
    @FXML
    private LineChart<Double, Double> profileChart;
    @FXML
    private LineChart<Double, Double> spectrumChart;
    @FXML
    private TableColumn<SourceSeries, String> columnLabel_1;
    @FXML
    private TableColumn<SourceSeries, String> columnLabel_2;
    @FXML
    private TableColumn<SourceSeries, String> columnLabel_3;
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
    @FXML
    public ChoiceBox windowData;

}
