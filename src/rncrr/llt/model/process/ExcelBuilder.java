package rncrr.llt.model.process;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Sidh on 24.02.2016.
 */
public class ExcelBuilder {

    private File excelFile;
    private Workbook workbook;

    public ExcelBuilder(File excelFile, Workbook workbook) {
        this.excelFile = excelFile;
        this.workbook = workbook;
    }

    public void createSheet(String nameSheet, List<Map<String, Object>> dataList) {
        Sheet sheet = workbook.createSheet(nameSheet);
        setSheetValue(sheet, dataList);
    }

    private void setSheetValue(Sheet sheet, List<Map<String, Object>> dataList) {
        Row row = sheet.createRow(0);

    }


}
