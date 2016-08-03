package rncrr.llt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rncrr.llt.model.service.utils.Config;
import rncrr.llt.model.service.utils.AlertService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BeamAnalyzer extends Application {

    /**
     * Method to start the application
     * @param primaryStage - object Stage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("appres/profile.fxml"));
            primaryStage.setTitle(Config.getStringProperty("app.title", "Digital BeamAnalyzer Processing Laboratory"));
            Scene scene = new Scene(root, Config.getIntProperty("app.width", 700), Config.getIntProperty("app.height", 500));
            scene.getStylesheets().add(getClass().getResource("appres/chart.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException | NullPointerException e) {
            AlertService.alertException("An error occurred while loading the application", e);
        }
    }


    public static void main(String[] args) {
        try{
            Config.loadConfig("resources/profile.properties");

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
            System.out.println("Date: "+dateFormat.format(new Date()));
            System.out.println("#######################################################################################");
            System.out.println("####################### create a new instance of the application ######################");
            System.out.println("#######################################################################################");
        } catch (IOException ioe){
            AlertService.alertException("An error occurred while loading the application", ioe);
        }
        launch(args);
    }
//
//    /**
//     * �������������� ��������� ������ ����������� � ������ � ����������  out � err
//     * � ����� �������� ���� �� ������� ��� ���������. ���� ������������ ���������� user.home
//     * @param loghome String ���� � �������
//     * @param nameOut String ��� ����� ������
//     * @param nameErr String ��� ����� ������ ������
//     */
//    static public void homeLog(String loghome,String nameOut, String nameErr) {
//        try {
//            File file_mess = new File(loghome);
//            if (!file_mess.exists())
//                if (!file_mess.mkdir()) throw new Exception("Unable to create add directory for logs");
//
//            OutputStream sos = new FileOutputStream(loghome + "/"+nameOut);
//            OutputStream eos = new FileOutputStream(loghome + "/"+nameErr);
//
//            System.setOut(new java.io.PrintStream(sos));
//            System.setErr(new java.io.PrintStream(eos));
//        } catch (FileNotFoundException fnfo) {
//            fnfo.printStackTrace();
//        }
//        catch (Exception fnfo) {
//            fnfo.printStackTrace();
//        }
//    }

}
