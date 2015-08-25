package rncrr.llt.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gillius.jfxutils.JFXUtil;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import rncrr.llt.model.utils.eobject.ECharts;
import rncrr.llt.model.utils.eobject.EMeasureType;
import rncrr.llt.model.bean.SourceSeries;
import rncrr.llt.model.utils.Config;
import rncrr.llt.view.api.ICharts;
import rncrr.llt.view.api.IDataSave;
import rncrr.llt.view.api.IDataTable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import rncrr.llt.view.charts.LineMarkerChart;
import rncrr.llt.view.utils.VUtil;

import java.util.Objects;


public class View {

    private static final Logger log = LogManager.getLogger(View.class);


    private IDataSave dataSave;
    private IDataTable dataTable;
    private ICharts chart;
    private LineMarkerChart spectrumChart;
    private ObservableList<XYChart.Series<Number, Number>> pChart;
    private ObservableList<XYChart.Series<Number, Number>> sChart;


    /**
     * Constructor - initialize objects VDataTable и VDataChart
     */
    public View() {
        log.trace("Entering into class -> View");
        dataSave = new VDataSave();
        log.trace("Initialize the new object -> VDataTable");
        dataTable = new VDataTable();
        log.trace("Initialize the new object -> VChart");
        pChart = FXCollections.observableArrayList();
        sChart = FXCollections.observableArrayList();
        chart = new VCharts(pChart, sChart);
    }

    @FXML
    protected void initialize(){
        spectrumChart = new LineMarkerChart(new NumberAxis(), new NumberAxis());
        vboxCharts.getChildren().add(spectrumChart);
        ChartPanManager panner = new ChartPanManager( spectrumChart );
        panner.setMouseFilter( new EventHandler<MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ) {
                if ( mouseEvent.getButton() == MouseButton.SECONDARY ||
                        ( mouseEvent.getButton() == MouseButton.PRIMARY &&
                                mouseEvent.isShortcutDown() ) ) {
                    //let it through
                } else {
                    mouseEvent.consume();
                }
            }
        } );
        panner.start();

        JFXChartUtil.setupZooming(spectrumChart, new EventHandler<MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ) {
                if ( mouseEvent.getButton() != MouseButton.PRIMARY ||  mouseEvent.isShortcutDown() )
                    mouseEvent.consume();
            }
        });
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
            chart.initChart(profileChart);
            chart.initChart(spectrumChart);
        } catch (Exception e) {
            log.error("An error occurred in the method View.openFileData", e);
            VUtil.alertException("An error occurred while trying to open and read the file", e);
        }
    }

    public void saveFileData(ActionEvent actionEvent) {
        log.trace("");
        try{
            dataSave.dataSave(dataTable);
        } catch (Exception e){
            log.error("An error occurred in the method View.saveFileData",e);
            VUtil.alertException("An error occurred while trying to save data",e);
        }
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
                chart.buildingProfileChart(seriesTableView, profileChart, windowData, "NEW");
                chart.clearChart(spectrumChart);
                chart.initChart(spectrumChart);
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
                chart.clearChart(profileChart);
                chart.clearChart(spectrumChart);
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
            chart.buildingSpectrumChart(seriesTableView, spectrumChart, ECharts.WINDOW, windowData, "NEW");
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
            chart.buildingSpectrumChart(seriesTableView, spectrumChart, ECharts.SPECTRUM, windowData, "NEW");
        } catch (Exception e) {
            log.error("An error occurred in the method View.transformData", e);
            VUtil.alertException("An error occurred while transformation data", e);
        }
    }

    /**
     *
     */
    public void inverseTransformData(ActionEvent actionEvent) {
        log.trace("");
        try{
            chart.buildingProfileChart(seriesTableView, profileChart, windowData, "");
        } catch (Exception e){
            log.error("An error occurred in the method View.inverseTransformData", e);
            VUtil.alertException("An error occurred while inverse transformation data", e);
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

    @FXML private TableView<SourceSeries> seriesTableView;
    @FXML private VBox vboxCharts;
    @FXML private LineChart<Number, Number> profileChart;
    @FXML public ChoiceBox windowData;
    @FXML private TableColumn<SourceSeries, String> columnLabel_1;
    @FXML private TableColumn<SourceSeries, String> columnLabel_2;
    @FXML private TableColumn<SourceSeries, String> columnLabel_3;
    @FXML private Label rowLabel_0;
    @FXML private Label rowLabel_1;
    @FXML private Label rowLabel_2;
    @FXML private Label rowLabel_3;
    @FXML private Label rowLabel_4;
    @FXML private Label rowLabel_5;
    @FXML private Label rowLabel_6;
    @FXML private Label rowLabel_7;
    @FXML private Label rowLabel_8;
    @FXML private Label rowLabel_9;
    @FXML private Label rowValue_0;
    @FXML private Label rowValue_1;
    @FXML private Label rowValue_2;
    @FXML private Label rowValue_3;
    @FXML private Label rowValue_4;
    @FXML private Label rowValue_5;
    @FXML private Label rowValue_6;
    @FXML private Label rowValue_7;
    @FXML private Label rowValue_8;
    @FXML private Label rowValue_9;

}
