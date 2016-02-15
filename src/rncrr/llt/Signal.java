package rncrr.llt;

import rncrr.llt.model.utils.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import rncrr.llt.view.utils.VUtil;


public class Signal extends Application {

    /**
     * Method to start the application
     * @param primaryStage - object Stage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("appres/profile.fxml"));
            primaryStage.setTitle(Config.getStringProperty("app.title", "Digital Signal Processing Laboratory"));
            Scene scene = new Scene(root, Config.getDoubleProperty("app.width", 700), Config.getDoubleProperty("app.height", 500));
            scene.getStylesheets().add(getClass().getResource("appres/chart.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException | NullPointerException e) {
            VUtil.alertException("An error occurred while loading the application",e);
        }
    }


    public static void main(String[] args) {
        homeLog("logs", "out.log", "err.log");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
        System.out.println("Date: "+dateFormat.format(new Date()));
        System.out.println("#######################################################################################");
        System.out.println("####################### create a new instance of the application ######################");
        System.out.println("#######################################################################################");
        try{
            Config.loadConfig("resources/profile.properties");
        } catch (IOException ioe){
            VUtil.alertException("An error occurred while loading the application",ioe);
        }
        launch(args);
    }

    /**
     * Переопределяет системные выводы стандартный и ошибок в переменные  out и err
     * А также изменяет пути по которым они выводятся. Пути определяются переменной user.home
     * @param loghome String путь в системе
     * @param nameOut String Имя файла вывода
     * @param nameErr String Имя файла вывода ошибок
     */
    static public void homeLog(String loghome,String nameOut, String nameErr) {
        try {
            File file_mess = new File(loghome);
            if (!file_mess.exists())
                if (!file_mess.mkdir()) throw new Exception("Unable to create add directory for logs");

            OutputStream sos = new FileOutputStream(loghome + "/"+nameOut);
            OutputStream eos = new FileOutputStream(loghome + "/"+nameErr);

            System.setOut(new java.io.PrintStream(sos));
            System.setErr(new java.io.PrintStream(eos));
        } catch (FileNotFoundException fnfo) {
            fnfo.printStackTrace();
        }
        catch (Exception fnfo) {
            fnfo.printStackTrace();
        }
    }

}
