package etalas.rncrr.view;

import etalas.rncrr.model.bean.Series;
import etalas.rncrr.view.api.IDataChart;
import etalas.rncrr.view.api.IDataTable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;



public class View {

    @FXML
    private TableView<Series> seriesTableView;
    @FXML
    private TableColumn<Series, String> scanIdColumn;
    @FXML
    private TableColumn<Series, String> machineNameColumn;
    @FXML
    private TableColumn<Series, String> energyColumn;
    @FXML
    private Label scanIdLabel;
    @FXML
    private Label machineNameLabel;
    @FXML
    private Label beamTypeLabel;
    @FXML
    private Label beamEnergyLabel;
    @FXML
    private Label ssdLabel;
    @FXML
    private Label fieldSizeLabel;
    @FXML
    private Label axisLabel;
    @FXML
    private Label depthLabel;
    @FXML
    private Label stepLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private LineChart<Double, Double> profileChart;

    private IDataTable dataTable;
    private IDataChart dataChart;

    public View() {
        dataTable = new VDataTable();
        dataChart = new VDataChart();
    }

    public void openFileData(ActionEvent actionEvent) {
        seriesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        dataTable.viewDataTable(seriesTableView, scanIdColumn, machineNameColumn, energyColumn);
        dataChart.initChart(profileChart);
    }

    public void detailRows(Event event) {
        if(!seriesTableView.getItems().isEmpty()) {
            Series series = seriesTableView.getSelectionModel().getSelectedItem();
            scanIdLabel.setText(series.getScanId());
            machineNameLabel.setText(series.getMachineName());
            beamTypeLabel.setText(series.getBeamType());
            beamEnergyLabel.setText(series.getBeamEnergy());
            ssdLabel.setText(Integer.toString(series.getSsd()) + " mm");
            fieldSizeLabel.setText(series.getFieldSize() + " mm");
            axisLabel.setText(series.getAxis());
            depthLabel.setText(Integer.toString(series.getDepth()) + " mm");
            stepLabel.setText(Integer.toString(series.getStep()) + " mm");
            dateLabel.setText(series.getDate());

            dataChart.buildingChart(seriesTableView, profileChart);
        }
    }

    public void deleteRows(ActionEvent actionEvent) {
        if(!seriesTableView.getItems().isEmpty()){
            ObservableList<Series> selectedSeriesList = seriesTableView.getSelectionModel().getSelectedItems();
            for(Series series : selectedSeriesList) {
                dataTable.deleteRows(series);
                dataChart.clearChart(profileChart);
            }
        }
    }

    public void closeApplication(ActionEvent actionEvent) {
        System.exit(0);
    }
}
