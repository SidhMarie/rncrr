package etalas.rncrr.model.service.api;

import etalas.rncrr.model.bean.Series;
import etalas.rncrr.model.service.AscFileReader;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Sidh on 28.02.2015.
 */
public class AbstractFileReader {

    protected ObservableList<Series> seriesList;

    public void setSeriesList(ObservableList<Series> seriesList) {
        this.seriesList = seriesList;
    }

    public ObservableList<Series> read(String path) {
        if (!path.trim().isEmpty()) {
            try {
                InputStream stream = new FileInputStream(path);
                return new AscFileReader().parse(stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return seriesList;
    }

    protected ObservableList<Series> parse(InputStream stream) {
        String line;
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNext()) {
            line = scanner.nextLine();
            readLine(line);
            fill(line);
        }
        return seriesList;
    }

    protected void readLine(String line)
    {}

    protected void fill( String line)
    {}

}
