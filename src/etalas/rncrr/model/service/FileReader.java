package etalas.rncrr.model.service;

import etalas.rncrr.model.bean.Series;
import etalas.rncrr.model.process.FillSeries;
import etalas.rncrr.model.process.api.AbstractSeries;
import etalas.rncrr.model.process.api.IFillSeries;
import etalas.rncrr.model.service.api.IFileReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;


/**
 * Created by Sidh on 21.02.2015.
 */
public class FileReader implements IFileReader {

    private Series series;
    private IFillSeries fSeries;
    private ObservableList<Series> seriesList;


    public FileReader() {
        series = new Series();
        fSeries = new FillSeries();
        seriesList = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Series> read(String path) {
        if(!path.trim().isEmpty()){
            try{
                InputStream stream = new FileInputStream(path);
                return parse(stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private ObservableList<Series> parse(InputStream stream) {
        String line;
        int flag = 0;
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNext()) {
            line = scanner.nextLine();
            if(!line.trim().isEmpty()){
                if(line.equals(AbstractSeries.ESeries.BLOCK_START.getName())){
                    flag = 1;
                } else if(line.equals(AbstractSeries.ESeries.BLOCK_END.getName())){
                    flag = 2;
                } else if(line.equals(AbstractSeries.ESeries.FILE_END.getName())){
                    flag = 0;
                }
            }
            fill(line, flag);
        }
        return seriesList;
    }

    private void fill(String line, int flag) {
        switch (flag) {
            case 1 :
                fSeries.fillSeries(series, line);
                break;
            case 2 :
                seriesList.add(series);
                series = new Series();
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader();
        fr.read("C:\\PROJECTS\\java\\profile\\1.asc");
    }


}
