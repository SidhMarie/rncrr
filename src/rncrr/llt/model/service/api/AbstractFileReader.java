package rncrr.llt.model.service.api;

import rncrr.llt.model.bean.SSeries;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Sidh on 28.02.2015.
 */
public abstract class AbstractFileReader {

    protected ObservableList<SSeries> seriesList;

    public void setSeriesList(ObservableList<SSeries> seriesList) {
        this.seriesList = seriesList;
    }

    public ObservableList<SSeries> read(String path) {
        if (!path.trim().isEmpty()) {
            try {
                InputStream stream = new FileInputStream(path);
                parse(stream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return seriesList;
    }

    protected ObservableList<SSeries> parse(InputStream stream) {
        String line;
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNext()) {
            line = scanner.nextLine();
            readLine(line);
            fill(line);
        }
        return seriesList;
    }

    protected String getValue(String line, String key){
        return line.substring(line.indexOf(key) + key.length()).trim();
    }

    abstract protected void readLine(String line);

    abstract protected void fill( String line);

}
