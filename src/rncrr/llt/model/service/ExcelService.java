package rncrr.llt.model.service;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import rncrr.llt.model.bean.BeamData;
import rncrr.llt.model.service.api.IExcelService;
import rncrr.llt.model.service.utils.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Sidh on 24.02.2016.
 */
public class ExcelService implements IExcelService {

    private File excelFile;
    private Workbook workbook;
    private String sourceFileType;

    public static final String COUNT_COL = "export.xls.column.count";
    public static final String SOURCEX_COL = "export.xls.column.sourcex";
    public static final String SOURCEY_COL = "export.xls.column.sourcey";
    public static final String WINDOW_COL = "export.xls.column.window";
    public static final String AMPLITUDE_COL = "export.xls.column.amplitude";
    public static final String POWER_COL = "export.xls.column.power";
    public static final String FREQUENCY_COL = "export.xls.column.frequency";
    public static final String REBUILD_COL = "export.xls.column.rebuild";

    public ExcelService(File excelFile, String sourceFileType) {
        this.excelFile = excelFile;
        this.workbook = new HSSFWorkbook();
        this.sourceFileType = sourceFileType;
    }

    public void createSheet(String nameSheet, List<BeamData> dataList) {
        try {
            Sheet sheet = workbook.createSheet(nameSheet);
            if(sourceFileType.equals("asc")) {
                createDataTable(sheet, dataList);
            }
            FileOutputStream out = new FileOutputStream(excelFile);
            workbook.write(out);
            out.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createDataTable(Sheet sheet, List<BeamData> dataList) {
        Cell cell;
        CellStyle style;
        String key;
        Row row = sheet.createRow(0);
        List<String> eList = getColumn();
        createHeadDataTable(row, eList);
        for(int i=0; i<dataList.size(); i++) {
            row = sheet.createRow(i+1);
            BeamData rs = dataList.get(i);
            if(rs != null)
            for(int k = 0; k < eList.size(); k++) {
                key = eList.get(k);
                cell = row.createCell(k);
                style = workbook.createCellStyle();
                if(!Objects.equals(key,COUNT_COL)) {
                    style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                } else {
                    style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                }
                cell.setCellStyle(style);
                switch (key) {
                    case COUNT_COL:
                        cell.setCellValue(rs.getCount());
                        break;
                    case SOURCEX_COL:
                        cell.setCellValue(rs.getSourceX());
                        break;
                    case SOURCEY_COL:
                        cell.setCellValue(rs.getSourceY());
                        break;
                    case WINDOW_COL:
                        cell.setCellValue(rs.getWindowX());
                        break;
                    case AMPLITUDE_COL:
                        cell.setCellValue(rs.getAmplitude());
                        break;
                    case POWER_COL:
                        cell.setCellValue(rs.getAmplitude());
                        break;
                    case FREQUENCY_COL:
                        cell.setCellValue(rs.getFrequency());
                        break;
                    case REBUILD_COL:
                        if (rs.getRebuild() != null)
                            cell.setCellValue(rs.getRebuild());
                        break;
                }
            }
        }
    }

    private void createHead() {

    }

    private void createHeadDataTable(Row row, List<String> eList){
        Cell cell;
        for(int i = 0; i < eList.size(); i++){
            cell = row.createCell(i);
            cell.setCellValue(Config.getStringProperty(eList.get(i)));
        }
    }

    private List<String> getColumn(){
        List<String> result = new ArrayList<>();
        if(Config.isProperties(COUNT_COL))
            result.add(COUNT_COL);
        if(Config.isProperties(SOURCEX_COL))
            result.add(SOURCEX_COL);
        if(Config.isProperties(SOURCEY_COL))
            result.add(SOURCEY_COL);
        if(Config.isProperties(WINDOW_COL))
            result.add(WINDOW_COL);
        if(Config.isProperties(AMPLITUDE_COL))
            result.add(AMPLITUDE_COL);
        if(Config.isProperties(POWER_COL))
            result.add(POWER_COL);
        if(Config.isProperties(FREQUENCY_COL))
            result.add(FREQUENCY_COL);
        if(Config.isProperties(REBUILD_COL))
            result.add(REBUILD_COL);
        return result;
    }

}
