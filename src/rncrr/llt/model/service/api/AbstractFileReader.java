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

    protected ObservableList<SSeries> seriesList;
    private static final Logger log = LogManager.getLogger(AbstractFileReader.class);

    /**
     *
     * @param seriesList
     */
    public void setSeriesList(ObservableList<SSeries> seriesList) {
        this.seriesList = seriesList;
    }

    /**
     *
     * @param path
     * @return
     */
    public ObservableList<SSeries> read(String path) throws Exception {
        log.trace("Entering into method -> read");
        if (!path.trim().isEmpty()) {
            InputStream stream = new FileInputStream(path);
            parse(stream);
        }
        return seriesList;
    }

    /**
     *
     * @param stream
     * @return
     */
    protected ObservableList<SSeries> parse(InputStream stream) throws Exception {
        log.trace("Entering into method -> parse");
        String line;
        log.trace("Create object -> Scanner");
        Scanner scanner = new Scanner(stream);
        log.trace("Try to read the lines in a loop and fill series");
        while(scanner.hasNext()) {
            line = scanner.nextLine();
            readLine(line);
            fill(line);
        }
        log.trace("Return object -> seriesList");
        return seriesList;
    }

    /**
     *
     * @param line
     * @param key
     * @return
     */
    protected String getValue(String line, String key) {
        return line.substring(line.indexOf(key) + key.length()).trim();
    }

    /**
     *
     * @param line
     */
    abstract protected void readLine(String line);

    /**
     *
     * @param line
     */
    abstract protected void fill( String line);

}
