package rncrr.llt;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;
import rncrr.llt.model.bean.AscSourceSeries;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.model.bean.eobject.EFilter;
import rncrr.llt.model.bean.eobject.EMeasureType;
import rncrr.llt.model.service.ChartsService;
import rncrr.llt.model.service.DataSaveService;
import rncrr.llt.model.service.TableService;
import rncrr.llt.model.service.ToolBarService;
import rncrr.llt.model.service.api.IChartsService;
import rncrr.llt.model.service.utils.Config;
import rncrr.llt.model.service.api.IDataSaveService;
import rncrr.llt.model.service.api.ITableService;
import rncrr.llt.model.service.utils.AlertService;

import java.io.File;
import java.util.Objects;


public class View {

    private IDataSaveService dataSave;
    private IChartsService chart;
    private ITableService dataTable;
    private ToolBarService toolBar;

    private boolean inverseFlag = false;

    private static final String TAB_DAT_FILE = "datFileTab";
    private static final String TAB_ASC_FILE = "ascFileTab";

    private static final Logger log = LogManager.getLogger(View.class);

    /**
     * Constructor - initialize objects VDataTable и VDataChart
     */
    public View() {
        log.trace("Entering into class -> View");
        dataSave = new DataSaveService();
        dataTable = new TableService();
        chart = new ChartsService();
        toolBar = new ToolBarService();
    }

    @FXML
    protected void initialize(){
        ChartPanManager panner = new ChartPanManager( spectrumChart );
        panner.setMouseFilter(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.SECONDARY ||
                        (mouseEvent.getButton() == MouseButton.PRIMARY &&
                                mouseEvent.isShortcutDown())) {
                    //let it through
                } else {
                    mouseEvent.consume();
                }
            }
        });
        panner.start();

        JFXChartUtil.setupZooming(spectrumChart, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() != MouseButton.PRIMARY || mouseEvent.isShortcutDown())
                    mouseEvent.consume();
            }
        });

        windowLimit.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        try {
                            chart.clearChart(spectrumChart);
                            if(profileChart.getData().size() == 2)
                                profileChart.getData().remove(1);
                            toolBar.changeWindowsLimit();

                        } catch (Exception e) {
                            e.printStackTrace(); //todo
                        }
                    }
                }
        );

        optimalFilter.getSelectionModel().selectedIndexProperty().addListener(
            new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    try {
                        if (checkBoxUseOF.isSelected()) {
                            chart.setFilterType(EFilter.getNameByIndex(newValue));
                            buildSpectrumChart(windowLimit);
                        }
                    } catch (Exception e) {
                        e.printStackTrace(); //todo
                    }
                }
            }
        );

        chart.setChartProperty(frequencySlider, meanValueFilter, stdValueFilter);

        toolBar.initToolBarElements(saveFileData, deleteRows, transformData, inverseData, exportToExcel,
                checkBoxAllWindows, checkBoxUseOF, windowLimit, optimalFilter,
                meanValueFilter, stdValueFilter, frequencySlider);
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
                    setPropForOpenFile(false, true, ascFileTab, profileChart);
                    dataTable.viewDataTable(file, seriesAscTableView, columnLabel_1, columnLabel_2, columnLabel_3);
                } else if(file.getName().contains(".dat")){
                    setPropForOpenFile(true, false, datFileTab, profileChart);
                    dataTable.viewDataTable(file, seriesDatTableView, columnLabelDat_1, columnLabelDat_2);
                }
            }
        } catch (Exception e) {
            log.error("An error occurred in the method View.openFileData", e);
            AlertService.alertException("An error occurred while trying to open and read the file", e);
        }
    }

    public void saveFileData(ActionEvent actionEvent) {
        try{
            dataSave.dataSave(dataTable);
        } catch (Exception e){
            log.error("An error occurred in the method View.saveFileData", e);
            AlertService.alertException("An error occurred while trying to save data", e);
        }
    }

    /**
     * Method fills in the table details the series and plotted according to series
     */
    public void detailSelectedRow(Event event) {
        log.trace("Entering into method -> View.detailSelectedRow");
        saveFileData.setDisable(false);
        try{
            if(isSelectedTab(TAB_DAT_FILE) && isSelectedRow(seriesDatTableView)) {
                chart.buildingProfileChart(seriesDatTableView, profileChart, true);
            } else
                if(isSelectedTab(TAB_ASC_FILE) && isSelectedRow(seriesAscTableView)) {
                    setAscDataGridValue((AscSourceSeries) seriesAscTableView.getSelectionModel().getSelectedItem());
                    chart.buildingProfileChart(seriesAscTableView, profileChart, true);
                } else {
                    AlertService.alertMessage("You must select row for view.");
                }
            chart.initChart(spectrumChart);

            toolBar.stateCheckRow();

        } catch (Exception e) {
            log.error("An error occurred in the method View.detailSelectedRow. ", e);
            AlertService.alertException("An error occurred while trying to view detail data and building chart", e);
        }
    }

    /**
     * The method removes the selected row in the list of series
     * Clears the graph and table detailed use information
     */
    public void deleteRows(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.deleteRows");
        try{
            if (isSelectedTab(TAB_DAT_FILE) && isSelectedRow(seriesDatTableView)) {
                dataTable.deleteRows(seriesDatTableView);
            } else
                if (isSelectedTab(TAB_ASC_FILE) && isSelectedRow(seriesAscTableView)) {
                    dataTable.deleteRows(seriesAscTableView);
                    clearDataGridValue();
                } else {
                    AlertService.alertMessage("You must select a row to remove");
                    return;
                }
            chart.clearChart(profileChart);
            chart.clearChart(spectrumChart);

            toolBar.stateDeleteRow();
        } catch (Exception e){
            log.error("An error occurred in the method View.deleteRows ", e);
            AlertService.alertException("An error occurred while deleting a row", e);
        }

    }

    /**
     *
     */
    public void transformData(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.transformData");
        try {
            if( checkBoxAllWindows.isSelected() ) {
                checkBoxAllWindows.selectedProperty().setValue(false);
            }
            if( checkBoxUseOF.isSelected() ) {
                checkBoxUseOF.selectedProperty().setValue(false);
                checkedFilter(false);
            }
            buildSpectrumChart(windowLimit);
            buildProfileChart(true);
            inverseFlag = true; // можно делать обратное преобразование
            toolBar.stateTransform();
        } catch (Exception e) {
            log.error("An error occurred in the method View.transformData", e);
            AlertService.alertException("An error occurred while transformation data", e);
        }
    }

    /**
     *
     */
    public void inverseTransformData(ActionEvent actionEvent) {
        log.trace("Entering into method -> View.inverseTransformData");
        try{
            if(inverseFlag) { // todo
                buildProfileChart(false);
//                inverseFlag = false;
            } else {
                AlertService.alertMessage("You must first build a spectrum chart or the reconstructed signal is already built. Check the element - show all windows.");
            }
        } catch (Exception e){
            log.error("An error occurred in the method View.inverseTransformData", e);
            AlertService.alertException("An error occurred while inverse transformation data", e);
        }
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

    /**
     *
     * @param actionEvent
     */
    public void showAllWindows(ActionEvent actionEvent) {
        try{
            if(checkBoxAllWindows.isSelected()){
                buildSpectrumChart(null);
                toolBar.clearFilter();
                toolBar.setInverseDataDisable(true);
                inverseFlag = false;
            } else {
                buildSpectrumChart(windowLimit);
                toolBar.setCheckBoxUseOFDisable(false);
                toolBar.setInverseDataDisable(false);
                inverseFlag = true;
            }
        } catch(Exception e){
            AlertService.alertException("An error occurred while inverse transformation data", e);
        }
    }

    /**
     *
     * @param actionEvent
     */
    public void useWienerFilter(ActionEvent actionEvent) {
        try{
            if(checkBoxUseOF.isSelected()){
                checkedFilter(true);
            } else {
                checkedFilter(false);
                buildSpectrumChart(windowLimit);
            }
        } catch (Exception e) {
            AlertService.alertException("An error occurred while use wiener filter", e);
        }
    }

    /**
     * The method of exporting data to Excel file
     */
    public void exportToExcel(ActionEvent actionEvent) {
        try {
//            if(){
//                SourceDataService.printData();
//                AlertService.alertMessage("Export data to xls file successfully completed");
//            } else {
//                AlertService.alertMessage("No data to export");
//            }
        } catch (Exception e){
            AlertService.alertException("An error occurred while export to xls file", e);
        }
    }

    /**
     * The method set properties for file type
     * @param ascTabFlag
     * @param datTabFlag
     * @param tab
     * @param lChart
     * @throws Exception
     */
    private void setPropForOpenFile(boolean ascTabFlag, boolean datTabFlag, Tab tab, LineChart<Number, Number> lChart) throws Exception {
        ascFileTab.setDisable(ascTabFlag);
        datFileTab.setDisable(datTabFlag);
        tabPane.getSelectionModel().select(tab);
        chart.initChart(lChart);
    }

    /**
     *
     * @param windowData
     */
    private void buildSpectrumChart(ChoiceBox windowData) throws Exception {
        if(isSelectedTab(TAB_DAT_FILE)) {
            chart.buildingSpectrumChart(seriesDatTableView, spectrumChart, windowData);
        } else
            if(isSelectedTab(TAB_ASC_FILE)){
                chart.buildingSpectrumChart(seriesAscTableView, spectrumChart, windowData);
            } else {
                AlertService.alertMessage("Failed to build spectrum chart.");
            }
    }

    /**
     *
     * @param flag
     */
    private void buildProfileChart(boolean flag) throws Exception {
        if(isSelectedTab(TAB_DAT_FILE)) {
            chart.buildingProfileChart(seriesDatTableView, profileChart,flag);
        } else
            if(isSelectedTab(TAB_ASC_FILE)) {
                chart.buildingProfileChart(seriesAscTableView, profileChart, flag);
            } else {
                AlertService.alertMessage("Failed to build profile chart.");
            }
    }

    /**
     *
     * @param flag
     */
    private void checkedFilter(boolean flag) {
        toolBar.checkedFilter(flag);
        if(flag) {
            chart.setFilterType(EFilter.getNameByIndex(optimalFilter.getSelectionModel().selectedIndexProperty().get()));
        }
    }

    /**
     *
     * @param tabId
     * @return true or false
     */
    private boolean isSelectedTab(String tabId){
        return Objects.equals(tabPane.getSelectionModel().getSelectedItem().getId(), tabId);
    }

    /**
     *
     * @param tableView
     * @return true or false
     */
    private boolean isSelectedRow(TableView<ISourceSeries> tableView) {
        return tableView.getSelectionModel().getSelectedItem() != null;
    }


    /**
     * The method sets the information fields of the table depending on the type of measurement.
     * @param series - object type SSeries
     */
    private void setAscDataGridValue(AscSourceSeries series) {
        if(series != null){
            if (Objects.equals(series.getType(), EMeasureType.OPP.name())
                    || Objects.equals(series.getType(), EMeasureType.OPD.name())) {
                setValues4OPPDataGrid(series);
            } else if (Objects.equals(series.getType(), EMeasureType.DDOE.name())
                    || Objects.equals(series.getType(), EMeasureType.DDAE.name())
                    || Objects.equals(series.getType(), EMeasureType.POE.name())) {
                setValues4DDOEDataGrid(series);
            }
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

    //Button for top toolbar
    @FXML private Button saveFileData;
    @FXML private Button openFileData;
    @FXML private Button deleteRows;
    @FXML private Button transformData;
    @FXML private Button inverseData;
    @FXML private Button exportToExcel;

    @FXML private ChoiceBox windowLimit;
    @FXML private ChoiceBox optimalFilter;

    // tab table data
    @FXML private TabPane tabPane;
    @FXML private Tab ascFileTab;
    @FXML private Tab datFileTab;
    @FXML private Label fileName;
    @FXML private TableView<ISourceSeries> seriesAscTableView;
    @FXML private TableView<ISourceSeries> seriesDatTableView;

    // profile chart
    @FXML private LineChart<Number, Number> profileChart;

    // spectrum chart
    @FXML private LineChart<Number, Number> spectrumChart;

    @FXML private Label meanValueFilter;
    @FXML private Label stdValueFilter;

    @FXML private CheckBox checkBoxAllWindows;
    @FXML private CheckBox checkBoxUseOF;

    @FXML private Slider frequencySlider;

    // asc table data
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
