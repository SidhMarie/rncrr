package etalas.rncrr.model.service.api;

import etalas.rncrr.model.bean.Series;
import javafx.collections.ObservableList;

/**
 * Created by Sidh on 21.02.2015.
 */
public interface IFileReader {

    public ObservableList<Series> read(String path);

}
