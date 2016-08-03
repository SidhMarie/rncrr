package rncrr.llt.model.service;

import javafx.scene.control.*;

/**
 * Created with IntelliJ IDEA.
 * User: Sidh
 * Date: 27.07.16
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class ToolBarService {

    private Button saveFileData;
    private Button deleteRows;
    private Button transformData;
    private Button inverseData;
    private Button exportToExcel;

    private CheckBox checkBoxAllWindows;
    private CheckBox checkBoxUseOF;

    private ChoiceBox windowLimit;
    private ChoiceBox optimalFilter;

    private Label meanValueFilter;
    private Label stdValueFilter;
    private Slider frequencySlider;

    public ToolBarService() {}

    public void initToolBarElements(Button saveFileData, Button deleteRows, Button transformData, Button inverseData, Button exportToExcel,
                          CheckBox checkBoxAllWindows, CheckBox checkBoxUseOF, ChoiceBox windowLimit,
                          ChoiceBox optimalFilter,
                          Label meanValueFilter,
                          Label stdValueFilter,
                          Slider frequencySlider) {
        this.saveFileData = saveFileData;
        this.deleteRows = deleteRows;
        this.transformData = transformData;
        this.inverseData = inverseData;
        this.exportToExcel = exportToExcel;
        this.checkBoxAllWindows = checkBoxAllWindows;
        this.checkBoxUseOF = checkBoxUseOF;
        this.windowLimit = windowLimit;
        this.optimalFilter = optimalFilter;
        this.meanValueFilter = meanValueFilter;
        this.stdValueFilter = stdValueFilter;
        this.frequencySlider = frequencySlider;
    }

    public void stateCheckRow() {
        // верхний toolbar
        setSaveFileDataDisable(false);
        setDeleteRows(false);
        setWindowLimitDisable(false);
        setTransformDataDisable(false);
        setInverseDataDisable(true);
        setExportToExcelDisable(true);
        // нижний toolbar
        clearAllWindowsCheck();
        setCheckBoxUseOFDisable(true);
        setOptimalFilterDisable(true);
        clearMeanValueFilter();
        clearStdValueFilter();
    }

    public void stateDeleteRow() {
        // верхний toolbar
        setSaveFileDataDisable(false);
        setDeleteRows(false);
        setWindowLimitDisable(true);
        setTransformDataDisable(true);
        setInverseDataDisable(true);
        setExportToExcelDisable(true);
        // нижний toolbar
        clearAllWindowsCheck();
        setCheckBoxUseOFDisable(true);
        setOptimalFilterDisable(true);
        clearMeanValueFilter();
        clearStdValueFilter();
    }

    public void stateTransform() {
        // верхний toolbar
        setInverseDataDisable(false);
        setExportToExcelDisable(false);
        // нижний toolbar
        setCheckBoxAllWindowsDisable(false);
        setCheckBoxUseOFDisable(false);
        clearMeanValueFilter();
        clearStdValueFilter();
    }

    public void changeWindowsLimit(){
        setInverseDataDisable(true);
        setExportToExcelDisable(true);
        clearAllWindowsCheck();
        clearFilter();
    }

    public void checkedFilter(boolean flag) {
        if(flag){
            optimalFilter.setDisable(false);
            frequencySlider.setDisable(false);
            meanValueFilter.setText("");
            stdValueFilter.setText("");
        } else {
            frequencySlider.setDisable(true);
            optimalFilter.setDisable(true);
            meanValueFilter.setText("");
            stdValueFilter.setText("");
        }
    }

    public void clearAllWindowsCheck() { //todo посмотреть необхимость этого метода
        this.checkBoxAllWindows.selectedProperty().setValue(false);
        this.checkBoxAllWindows.setDisable(true);
    }

    public void clearFilter() {
        checkedFilter(false);
        this.checkBoxUseOF.selectedProperty().setValue(false);
        this.checkBoxUseOF.setDisable(true);
    }


    public void setSaveFileDataDisable(boolean flag) {
        this.saveFileData.setDisable(flag);
    }

    public void setDeleteRows(boolean flag) {
        this.deleteRows.setDisable(flag);
    }

    public void setTransformDataDisable(boolean flag) {
        this.transformData.setDisable(flag);
    }

    public void setInverseDataDisable(boolean flag) {
        this.inverseData.setDisable(flag);
    }

    public void setExportToExcelDisable(boolean flag) {
        this.exportToExcel.setDisable(flag);
    }

    public void setCheckBoxAllWindowsDisable(boolean flag) {
        this.checkBoxAllWindows.setDisable(flag);
    }

    public void setCheckBoxUseOFDisable(boolean flag) {
        this.checkBoxUseOF.setDisable(flag);
    }

    public void setWindowLimitDisable(boolean flag) {
        this.windowLimit.setDisable(flag);
    }

    public void setOptimalFilterDisable(boolean flag) {
        this.optimalFilter.setDisable(flag);
    }

    public void clearMeanValueFilter() {
        this.meanValueFilter.setText("");
    }

    public void clearStdValueFilter() {
        this.stdValueFilter.setText("");
    }
}
