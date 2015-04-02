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


public class Signal extends Application {

    private static final Logger log = LogManager.getLogger(Signal.class);

    private void initApplication() {
        try {
            Config.loadConfig("resources/profile.properties");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        initApplication();
        Parent root = FXMLLoader.load(getClass().getResource("appres/profile.fxml"));
        primaryStage.setTitle(Config.getStringProperty("app.title", "Additional Calculation Profiles"));
        Scene scene = new Scene(root, Config.getDoubleProperty("app.width", 700), Config.getDoubleProperty("app.height", 500));
        scene.getStylesheets().add(getClass().getResource("appres/chart.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}