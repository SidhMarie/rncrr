package rncrr.llt.model.process;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import rncrr.llt.model.bean.ReportSeries;
import rncrr.llt.model.utils.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;

/**
 * Created by Sidh on 24.02.2016.
 */
public class ExcelBuilder {

    private File excelFile;
    private Workbook workbook;

    public ExcelBuilder(File excelFile) {
        this.excelFile = excelFile;
        this.workbook = new HSSFWorkbook();
    }

    public void createSheet(String nameSheet, List<ReportSeries> dataList) {
        try {
            Sheet sheet = workbook.createSheet(nameSheet);
            setSheetValue(sheet, dataList);
            FileOutputStream out = new FileOutputStream(excelFile);
            workbook.write(out);
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setSheetValue(Sheet sheet, List<ReportSeries> dataList) {
        Cell cell;
        CellStyle style;
        String key;
        style = workbook.createCellStyle();
        Row row = sheet.createRow(0);
        for(int i = 0; i < Config.getExportColumn().size(); i++){
            key = (String) Config.getExportColumn().get(i);
            cell = row.createCell(i);
            cell.setCellValue(Config.getStringProperty(key));
            style.setFillForegroundColor(new HSSFColor.YELLOW().getIndex());
            style.setFillBackgroundColor(new HSSFColor.YELLOW().getIndex());
            cell.setCellStyle(style);
        }

        for(int i=0; i<dataList.size(); i++) {
            row = sheet.createRow(i+1);
            ReportSeries rs = dataList.get(i);
            if(rs != null)
            for(int k = 0; k < Config.getExportColumn().size(); k++){
                key = (String) Config.getExportColumn().get(k);
                cell = row.createCell(k);
                if(!Objects.equals(key,"export.xls.column.count")){
                    style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                    cell.setCellStyle(style);
                }
                switch (key) {
                    case "export.xls.column.count":
                        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                        cell.setCellStyle(style);
                        cell.setCellValue(rs.getCount());
                        break;
                    case "export.xls.column.sourcex":
                        cell.setCellValue(rs.getSourceX());
                        break;
                    case "export.xls.column.sourcey":
                        cell.setCellValue(rs.getSourceY());
                        break;
                    case "export.xls.column.window":
                        cell.setCellValue(rs.getWindowX());
                        break;
                    case "export.xls.column.amplitude":
                        cell.setCellValue(rs.getAmplitude());
                        break;
                    case "export.xls.column.frequency":
                        cell.setCellValue(rs.getFrequency());
                        break;
                    case "export.xls.column.rebuild":
                        if (rs.getRebuild() != null)
                            cell.setCellValue(rs.getRebuild());
                        break;
                }
            }
        }
    }


}
