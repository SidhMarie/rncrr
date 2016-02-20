package rncrr.llt.model.process.api;

import javafx.collections.ObservableList;
import rncrr.llt.model.bean.api.ISourceSeries;
import rncrr.llt.view.utils.VUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Sidh on 28.02.2015.
 */
public abstract class AbstractDataFile {

    public List<String> dataList;
    protected ObservableList<ISourceSeries> seriesList;

    /**
     * Method set the seriesList
     * @param seriesList - object type ObservableList
     */
    public void setSeriesList(ObservableList<ISourceSeries> seriesList) {
        this.seriesList = seriesList;
    }

    /**
     * Method get the path to file and opening Input Stream for reading him
     * @param path - path to file
     * @return seriesList - object type ObservableList
     */
    public ObservableList<ISourceSeries> read(String path) throws Exception {
        if (!path.trim().isEmpty()) {
            try(InputStream stream = new FileInputStream(path)) {
                parse(stream);
            }catch (Exception e){
                VUtil.printError("An error occurred while trying to open input stream or parse it -> method AbstractDataFile.read");
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
        String line;
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNext()) {
            line = scanner.nextLine();
            dataList.add(line);
        }
        readList();
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
     */
    abstract protected void readList();


}
