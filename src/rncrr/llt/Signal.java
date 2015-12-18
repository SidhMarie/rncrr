package rncrr.llt;

import rncrr.llt.model.utils.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import rncrr.llt.view.utils.VUtil;


public class Signal extends Application {

    private static final Logger log = LogManager.getLogger(Signal.class);

    /**
     * Method to start the application
     * @param primaryStage - object Stage
     */
    @Override
    public void start(Stage primaryStage) {
        log.trace("Entering into method -> Signal.start");
        try {
            log.trace("Try loading profile.fxml");
            Parent root = FXMLLoader.load(getClass().getResource("appres/profile.fxml"));
            log.trace("Set the application title");
            primaryStage.setTitle(Config.getStringProperty("app.title", "Digital Signal Processing Laboratory"));
            log.trace("Set the size of the application window");
            Scene scene = new Scene(root, Config.getDoubleProperty("app.width", 700), Config.getDoubleProperty("app.height", 500));
//            log.trace("Try loading chart.css");
//            scene.getStylesheets().add(getClass().getResource("appres/chart.css").toExternalForm());
            log.trace("Show application on the screen");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException | NullPointerException e) {
            log.error("An error occurred in the method Signal.start",e);
            VUtil.alertException("An error occurred while loading the application",e);
        }
    }


    public static void main(String[] args) {
        log.trace("#######################################################################################");
        log.trace("####################### create a new instance of the application ######################");
        log.trace("#######################################################################################");
        log.trace("Try loading profile.properties");
        try{
            Config.loadConfig("resources/profile.properties");
        } catch (IOException ioe){
            log.error("An error occurred in the method Signal.main",ioe);
            VUtil.alertException("An error occurred while loading the application",ioe);
        }
        launch(args);
    }
}
