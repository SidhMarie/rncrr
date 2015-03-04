package etalas.rncrr.view;

import etalas.rncrr.model.bean.Series;
import etalas.rncrr.model.process.Config;
import etalas.rncrr.view.api.IDataChart;
import etalas.rncrr.view.api.IDataTable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;


public class View {

    private IDataTable dataTable;
    private IDataChart dataChart;

    public View() {
        dataTable = new VDataTable();
        dataChart = new VDataChart();
    }

    public void openFileData(ActionEvent actionEvent) {
//        seriesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        dataTable.viewDataTable(seriesTableView, columnLabel_1, columnLabel_2, columnLabel_3);
        dataChart.initChart(profileChart);
    }

    public void detailRows(Event event) {
        if(!seriesTableView.getItems().isEmpty()) {
            Series series = seriesTableView.getSelectionModel().getSelectedItem();
            setAscDataGridValue(series);

            dataChart.buildingChart(seriesTableView, profileChart);
        }
    }

    public void deleteRows(ActionEvent actionEvent) {
        if(!seriesTableView.getItems().isEmpty()){
            dataTable.deleteRows(seriesTableView.getSelectionModel().getSelectedItems());
            dataChart.clearChart(profileChart);
            clearDataGridValue();
        }
    }

    public void closeApplication(ActionEvent actionEvent) {
        System.exit(0);
    }

    private void setAscDataGridValue(Series series){

    }

    private void setAsc4PPDataGridValue(Series series){
        rowLabel_0.setText(Config.getStringProperty("tg.row.label.scanId", "Scan ID"));
        rowLabel_1.setText(Config.getStringProperty("tg.row.label.machine", "Machine name"));
        rowLabel_2.setText(Config.getStringProperty("tg.row.label.beamType", "Beam type"));
        rowLabel_3.setText(Config.getStringProperty("tg.row.label.beamEnergy", "Beam energy"));
        rowLabel_4.setText(Config.getStringProperty("tg.row.label.ssd", "Ssd"));
        rowLabel_5.setText(Config.getStringProperty("tg.row.label.fieldSize", "Field size"));
        rowLabel_6.setText(Config.getStringProperty("tg.row.label.axis", "Axis"));
        rowLabel_7.setText(Config.getStringProperty("tg.row.label.depth", "Depth"));
        rowLabel_8.setText(Config.getStringProperty("tg.row.label.step", "Step"));
        rowLabel_9.setText(Config.getStringProperty("tg.row.label.date", "Date"));

        rowValue_0.setText(series.getScanId());
        rowValue_1.setText(series.getMachineName());
        rowValue_2.setText(series.getBeamType());
        rowValue_3.setText(series.getBeamEnergy());
        rowValue_4.setText(Integer.toString(series.getSsd()) + " mm");
        rowValue_5.setText(series.getFieldSize());
        rowValue_6.setText(series.getAxis());
        rowValue_7.setText(Integer.toString(series.getDepth()) + " mm");
        rowValue_8.setText(Integer.toString(series.getStep()) + " mm");
        rowValue_9.setText(series.getDate());
    }

    private void setAsc4EPDataGridValue(Series series){
        rowLabel_0.setText(Config.getStringProperty("tg.row.label.scanId", "Scan ID"));
        rowLabel_1.setText(Config.getStringProperty("tg.row.label.machine", "Machine name"));
        rowLabel_2.setText(Config.getStringProperty("tg.row.label.beamType", "Beam type"));
        rowLabel_3.setText(Config.getStringProperty("tg.row.label.beamEnergy", "Beam energy"));
        rowLabel_4.setText(Config.getStringProperty("tg.row.label.ssd", "Ssd"));
        rowLabel_5.setText(Config.getStringProperty("tg.row.label.fieldSize", "Field size"));
        rowLabel_6.setText(Config.getStringProperty("tg.row.label.axis", "Axis"));
        rowLabel_7.setText(Config.getStringProperty("tg.row.label.depth", "Depth"));
        rowLabel_8.setText(Config.getStringProperty("tg.row.label.step", "Step"));
        rowLabel_9.setText(Config.getStringProperty("tg.row.label.date", "Date"));

        rowValue_0.setText(series.getScanId());
        rowValue_1.setText(series.getMachineName());
        rowValue_2.setText(series.getBeamType());
        rowValue_3.setText(series.getBeamEnergy());
        rowValue_4.setText(Integer.toString(series.getSsd()) + " mm");
        rowValue_5.setText(series.getFieldSize());
        rowValue_6.setText(series.getAxis());
        rowValue_7.setText(Integer.toString(series.getDepth()) + " mm");
        rowValue_8.setText(Integer.toString(series.getStep()) + " mm");
        rowValue_9.setText(series.getDate());
    }

    private void clearDataGridValue(){
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
    private TableView<Series> seriesTableView;
    @FXML
    private TableColumn<Series, String> columnLabel_1;
    @FXML
    private TableColumn<Series, String> columnLabel_2;
    @FXML
    private TableColumn<Series, String> columnLabel_3;
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
    private LineChart<Double, Double> profileChart;

}
