package rncrr.llt.view;

import rncrr.llt.model.bean.eobject.EMeasureType;
import rncrr.llt.model.bean.SSeries;
import rncrr.llt.model.utils.Config;
import rncrr.llt.view.api.IDataChart;
import rncrr.llt.view.api.IDataTable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;

import java.util.Objects;


public class View {

    private IDataTable dataTable;
    private IDataChart dataChart;

    public View() {
        dataTable = new VDataTable();
        dataChart = new VDataChart();
    }

    public void openFileData(ActionEvent actionEvent) {
        dataTable.viewDataTable(seriesTableView, columnLabel_1, columnLabel_2, columnLabel_3);
        dataChart.initChart(profileChart);
        clearDataGridValue();
    }

    public void detailRows(Event event) {
        if(!seriesTableView.getItems().isEmpty()) {
            SSeries series = seriesTableView.getSelectionModel().getSelectedItem();
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

    private void setAscDataGridValue(SSeries series){
        if(Objects.equals(series.getType(), EMeasureType.OPP.name())
                || Objects.equals(series.getType(), EMeasureType.OPD.name()))
        {
            setAsc4OPPDataGridValue(series);
        } else if(Objects.equals(series.getType(), EMeasureType.DDOE.name())
                || Objects.equals(series.getType(), EMeasureType.DDAE.name())
                || Objects.equals(series.getType(), EMeasureType.POE.name()))
        {
            setAsc4DDOEDataGridValue(series);
        }
    }

    private void setAsc4OPPDataGridValue(SSeries series){
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

    private void setAsc4DDOEDataGridValue(SSeries series){
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

    private void clearDataGridValue(){
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
    private TableColumn<SSeries, String> columnLabel_1;
    @FXML
    private TableColumn<SSeries, String> columnLabel_2;
    @FXML
    private TableColumn<SSeries, String> columnLabel_3;
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
