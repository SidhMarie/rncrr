package rncrr.llt.model.process.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rncrr.llt.model.bean.SourceSeries;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Sidh on 28.02.2015.
 */
public abstract class AbstractAscFile {


    public List<String> dataLine;
    protected ObservableList<SourceSeries> seriesList;
    private static final Logger log = LogManager.getLogger(AbstractAscFile.class);


    /**
     * Method set the seriesList
     * @param seriesList - object type ObservableList
     */
    public void setSeriesList(ObservableList<SourceSeries> seriesList) {
        this.seriesList = seriesList;
    }

    /**
     * Method get the path to file and opening Input Stream for reading him
     * @param path - path to file
     * @return seriesList - object type ObservableList
     */
    public ObservableList<SourceSeries> read(String path) throws Exception {
        log.trace("Entering into method -> AbstractFileReader.read");
        if (!path.trim().isEmpty()) {
            log.trace("Try to open FileInputStream");
            try(InputStream stream = new FileInputStream(path)) {
                log.trace("Try to parse input stream");
                parse(stream);
            }catch (Exception e){
                log.error("An error occurred while trying to open input stream or parse it");
                throw new Exception(e);
            }
        }
        return seriesList;
    }

    /**
     * Method create Scanner and reading all lines
     * @param stream - Input Stream
     */
    protected void parse(InputStream stream) throws Exception {
        log.trace("Entering into method -> AbstractFileReader.parse");
        String line;
        log.trace("Create object -> Scanner");
        Scanner scanner = new Scanner(stream);
        log.trace("Try to read the lines in a loop and fill series");
        while(scanner.hasNext()) {
            line = scanner.nextLine();
            dataLine.add(line);
            readLine(line);
        }
        log.trace("The incoming stream read and successfully parsed");
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


}
