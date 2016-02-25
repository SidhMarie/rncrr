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

    public void createSheet(String nameSheet, List<Map<Double, Double>> dataList) {
        Sheet sheet = workbook.createSheet(nameSheet);
        setSheetValue(sheet, dataList);
    }

    private void setSheetValue(Sheet sheet, List<Map<Double, Double>> dataList) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Count");
        row.createCell(1).setCellValue("X value");
        row.createCell(2).setCellValue("Y value");
        row.createCell(3).setCellValue("X/100");

        for(int i=0; i<dataList.size(); i++){
            row = sheet.createRow(i+1);
            row.createCell(i).setCellValue(i+1);
//            row.createCell(i).setCellValue(dataList.get(i));
        }
    }


}
