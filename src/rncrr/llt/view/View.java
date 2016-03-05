package rncrr.llt.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import rncrr.llt.model.process.api.ISourceSeries;
import rncrr.llt.model.service.TransformService;
import rncrr.llt.model.utils.eobject.EMeasureType;
import rncrr.llt.model.bean.AscSourceSeries;
import rncrr.llt.model.utils.Config;
import rncrr.llt.view.api.ICharts;
import rncrr.llt.view.api.IDatTable;
import rncrr.llt.view.api.IDataSave;
import rncrr.llt.view.api.IAscTable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import rncrr.llt.view.utils.VUtil;

import java.io.File;
import java.util.Objects;


public class View {

    private IDataSave dataSave;
    private IAscTable ascTable;
    private IDatTable datTable;
    private ICharts chart;

    private boolean inverseFlag = false;

    private static final String TAB_DAT_FILE = "datFileTab";
    private static final String TAB_ASC_FILE = "ascFileTab";

    private static final Logger log = LogManager.getLogger(View.class);

    /**
     * Constructor - initialize objects VDataTable и VDataChart
     */
    public View() {
        log.trace("Entering into class -> View");
        dataSave = new VDataSave();
        ascTable = new VAscTable();
        datTable = new VDatTable();
        chart = new VCharts();
    }

    @FXML
    protected void initialize(){
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
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() != MouseButton.PRIMARY || mouseEvent.isShortcutDown())
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
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if(file != null){
                fileName.setText(fileName.getText() + file.getName());
                if(file.getName().contains(".asc")){
                    ascFileTab.setDisable(false);
                    datFileTab.setDisable(true);
                    tabPane.getSelectionModel().select(ascFileTab);
                    ascTable.viewDataTable(file, seriesTableView, columnLabel_1, columnLabel_2, columnLabel_3);
                    chart.initChart(profileChart);
                    chart.initChart(spectrumChart);
                } else if(file.getName().contains(".dat")){
                    ascFileTab.setDisable(true);
                    datFileTab.setDisable(false);
                    tabPane.getSelectionModel().select(datFileTab);
                    datTable.viewDataTable(file,seriesDatTableView, columnLabelDat_1,columnLabelDat_2);
                    chart.initChart(profileChart);
                    chart.initChart(spectrumChart);
                }
            }
        } catch (Exception e) {
            VUtil.printError("An error occurred in the method View.openFileData", e);
            VUtil.alertException("An error occurred while trying to open and read the file", e);
        }
    }

    public void saveFileData(ActionEvent actionEvent) {
        try{
            dataSave.dataSave(ascTable);
        } catch (Exception e){
            VUtil.printError("An error occurred in the method View.saveFileData", e);
            VUtil.alertException("An error occurred while trying to save data",e);
        }
    }

    /**
     * Method fills in the table details the series and plotted according to series
     */
    public void detailSelectedRow(Event event) {
        log.trace("Entering into method -> View.detailSelectedRow");
        try{
            if(Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), TAB_DAT_FILE)) {
                if (seriesDatTableView.getSelectionModel().getSelectedItem() != null) {
                    chart.buildingProfileChart(seriesDatTableView, profileChart, true);
                }
            } else if(Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), TAB_ASC_FILE)) {
                if (seriesTableView.getSelectionModel().getSelectedItem() != null) {
                    AscSourceSeries series = (AscSourceSeries) seriesTableView.getSelectionModel().getSelectedItem();
                    setAscDataGridValue(series);
                    chart.buildingProfileChart(seriesTableView, profileChart, true);
                }
            }
            chart.initChart(spectrumChart);
            checkBoxAllWindows.selectedProperty().setValue(false);
            checkBoxAllWindows.setDisable(true);
        } catch (Exception e) {
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
            if(Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), TAB_DAT_FILE)) {
                if (seriesDatTableView.getSelectionModel().getSelectedItem() != null) {
                    datTable.deleteRows(seriesDatTableView.getSelectionModel().getSelectedItems());
                } else {
                    VUtil.alertMessage("You must select a row to remove");
                    return;
                }
            } else if(Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), TAB_ASC_FILE)) {
                if (seriesTableView.getSelectionModel().getSelectedItem() != null) {
                    ascTable.deleteRows(seriesTableView.getSelectionModel().getSelectedItems());
                    clearDataGridValue();
                } else {
                    VUtil.alertMessage("You must select a row to remove");
                    return;
                }
            }
            chart.clearChart(profileChart);
            chart.clearChart(spectrumChart);
            checkBoxAllWindows.setDisable(true);
        } catch (Exception e){
            VUtil.alertException("An error occurred while deleting a row", e);
        }

    }

    /**
     * Method completes the execution of the application
     */
    public void closeApplication(ActionEvent actionEvent) { System.exit(0); }

    /**
     *
     */
    public void transformData(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.transformData");
        try {
            if(Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), TAB_DAT_FILE)) {
                chart.buildingSpectrumChart(seriesDatTableView, spectrumChart, windowData);
                chart.buildingProfileChart(seriesDatTableView, profileChart, true);
            } else
                if(Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), TAB_ASC_FILE)){
                    chart.buildingSpectrumChart(seriesTableView, spectrumChart, windowData);
                    chart.buildingProfileChart(seriesTableView, profileChart, true);
                }
            inverseFlag = true;
            checkBoxAllWindows.setDisable(false);
        } catch (Exception e) {
            VUtil.alertException("An error occurred while transformation data", e);
        }
    }

    /**
     *
     */
    public void inverseTransformData(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.inverseTransformData");
        try{
            if(inverseFlag) {
                if(Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), TAB_DAT_FILE)) {
                    chart.buildingProfileChart(seriesDatTableView, profileChart, false);
                } else
                    if(Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), TAB_ASC_FILE)) {
                        chart.buildingProfileChart(seriesTableView, profileChart, false);
                    }
                inverseFlag = false;
            } else {
                VUtil.alertMessage("You must first build a spectrum chart or the reconstructed signal is already built");
            }
        } catch (Exception e){
            VUtil.alertException("An error occurred while inverse transformation data", e);
        }
    }

    public void doFilterData(ActionEvent actionEvent) {
    }

    //TOOLBAR for charts
    /**
     *
     */
    public void autoSizeChart(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.autoSizeChart");
        spectrumChart.getXAxis().setAutoRanging(true);
        spectrumChart.getYAxis().setAutoRanging(true);
    }

    public void showAllWindows(ActionEvent actionEvent) {
        try{
            if(checkBoxAllWindows.isSelected()){
                if(Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), TAB_DAT_FILE)) {
                    chart.buildingSpectrumChart(seriesDatTableView, spectrumChart);
                } else
                    if(Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), TAB_ASC_FILE)) {
                        chart.buildingSpectrumChart(seriesTableView, spectrumChart);
                    }
            } else {
                if(Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), TAB_DAT_FILE)) {
                    chart.buildingSpectrumChart(seriesDatTableView, spectrumChart, windowData);
                } else
                if(Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), TAB_ASC_FILE)){
                    chart.buildingSpectrumChart(seriesTableView, spectrumChart, windowData);
                }
                inverseFlag = true;
            }
        } catch(Exception e){
            //TODO
            VUtil.alertException("An error occurred while inverse transformation data", e);
        }
    }

    public void exportToExcel(ActionEvent actionEvent) {
        try{
            TransformService.printData();
            VUtil.alertMessage("Export data to xls file successfully completed");
        } catch (Exception e){
            VUtil.alertException("An error occurred while export to xls file", e);
        }
    }


    /**
     * The method sets the information fields of the table depending on the type of measurement.
     * @param series - object type SSeries
     */
    private void setAscDataGridValue(AscSourceSeries series) {
        if (Objects.equals(series.getType(), EMeasureType.OPP.name())
                || Objects.equals(series.getType(), EMeasureType.OPD.name())) {
            setValues4OPPDataGrid(series);
        } else if (Objects.equals(series.getType(), EMeasureType.DDOE.name())
                || Objects.equals(series.getType(), EMeasureType.DDAE.name())
                || Objects.equals(series.getType(), EMeasureType.POE.name())) {
            setValues4DDOEDataGrid(series);
        }
    }

    /**
     * Method sets the values of the fields of the table details for type OPP и OPD
     * @param series - object type SSeries
     */
    private void setValues4OPPDataGrid(AscSourceSeries series) {
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
    private void setValues4DDOEDataGrid(AscSourceSeries series) {
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


    @FXML private TabPane tabPane;
    @FXML private TableView<ISourceSeries> seriesTableView;
    @FXML private TableView<ISourceSeries> seriesDatTableView;

    @FXML private LineChart<Number, Number> profileChart;
    @FXML private LineChart<Number, Number> spectrumChart;
    @FXML private ChoiceBox windowData;
    @FXML private Label fileName;
    @FXML private CheckBox checkBoxAllWindows;

    @FXML private Tab ascFileTab;
    @FXML private Tab datFileTab;

    @FXML private ToggleGroup spectrumType;
    @FXML private RadioButton radioAmplitude;
    @FXML private RadioButton radioSpectrumPower;


    @FXML private TableColumn<ISourceSeries, String> columnLabel_1;
    @FXML private TableColumn<ISourceSeries, String> columnLabel_2;
    @FXML private TableColumn<ISourceSeries, String> columnLabel_3;

    @FXML private TableColumn<ISourceSeries, String> columnLabelDat_1;
    @FXML private TableColumn<ISourceSeries, String> columnLabelDat_2;

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
