package rncrr.llt.model.service.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.SSeries;
import javafx.collections.ObservableList;
import rncrr.llt.view.utils.VUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Sidh on 28.02.2015.
 */
public abstract class AbstractFileReader {

    private static final Logger log = LogManager.getLogger(AbstractFileReader.class);

    protected ObservableList<SSeries> seriesList;

    public void setSeriesList(ObservableList<SSeries> seriesList) {
        this.seriesList = seriesList;
    }

    public ObservableList<SSeries> read(String path) {
        log.trace("Entering into method AbstractFileReader.read");
        if (!path.trim().isEmpty()) {
            try {
                InputStream stream = new FileInputStream(path);
                parse(stream);
            } catch (Exception e) {
                log.error("There was an error in the method AbstractFileReader.read", e);
                VUtil.alertException("An error occurred while reading the file", e);
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
