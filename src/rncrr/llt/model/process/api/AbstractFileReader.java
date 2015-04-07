package rncrr.llt.model.process.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger log = LogManager.getLogger(AbstractFileReader.class);

    /**
     * Method set the seriesList
     * @param seriesList - object type ObservableList
     */
    public void setSeriesList(ObservableList<SSeries> seriesList) {
        this.seriesList = seriesList;
    }

    /**
     * Method get the path to file and opening Input Stream for reading him
     * @param path - path to file
     * @return seriesList - object type ObservableList
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
     * Method create Scanner and reading all lines
     * @param stream - Input Stream
     */
    protected void parse(InputStream stream) throws Exception {
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
    }

    /**
     * Helper method to parse the string
     * @param line - input line
     * @param key - key for parse line
     * @return line without key
     */
    protected String getValue(String line, String key) {
        return line.substring(line.indexOf(key) + key.length()).trim();
    }

    /**
     * method to be implemented in the derived-class
     * @param line - input line
     */
    abstract protected void readLine(String line);

    /**
     * method to be implemented in the derived-class
     * @param line - input line
     */
    abstract protected void fill(String line);

}
