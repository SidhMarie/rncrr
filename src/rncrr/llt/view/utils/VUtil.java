package rncrr.llt.view.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import rncrr.llt.model.utils.eobject.EMessage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Sidh on 31.03.2015.
 */
public class VUtil {

    /**
     * Метод создает всплывающее окно с информационным сообщением
     * @param information - заголовок сообщения, в общем случае null
     * @param message - информационное сообщение
     */
    public static void alertMessage(String information, String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
        String messageText = dateFormat.format(new Date()) + "INFORMATION: " + message;
        System.out.println(messageText);
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(EMessage.INFORMATION.toString());
        alert.setHeaderText(information);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Метод создает всплывающее окно с информационным сообщением
     * @param message - информационное сообщение
     */
    public static void alertMessage(String message) {
        alertMessage(null, message);
    }

    /**
     * Метод создает всплывающее окно с
     * @param information - заголовок сообщения, в общем случае null
     * @param message - информационное сообщение
     */
    public static void alertWarning(String information, String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
        String warningText = dateFormat.format(new Date()) + "WARNING: " + message;
        System.out.println(warningText);
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(EMessage.WARNING.toString());
        alert.setHeaderText(information);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Метод создает всплывающее окно с
     * @param message - информационное сообщение
     */
    public static void alertWarning(String message){
        alertWarning(null, message);
    }

    /**
     * Метод создает всплывающее окно с
     * @param information - заголовок сообщения, в общем случае null
     * @param message - информационное сообщение
     */
    public static void alertError(String information, String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
        String exceptionText = dateFormat.format(new Date()) + "ERROR: " + message;
        System.err.println(exceptionText);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(EMessage.ERROR.toString());
        alert.setHeaderText(information);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Метод создает всплывающее окно с
     * @param message - информационное сообщение
     */
    public static void alertError(String message) {
        alertError(null, message);
    }

    /**
     * Метод создает всплывающее окно с
     * @param information - заголовок сообщения, в общем случае null
     * @param message - информационное сообщение
     * @param e - Exception
     */
    public static void alertException(String information, String message, Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(EMessage.EXCEPTION.toString());
        alert.setHeaderText(information);
        alert.setContentText(message);
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
        String exceptionLog = dateFormat.format(new Date()) + "ERROR: " + message + " ===>>> \n" + sw.toString();
        System.err.println(exceptionLog);

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    public static void alertException(String message, Exception e) {
        alertException(null, message, e);
    }

    public static void printError(String message, Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
        String exceptionLog = dateFormat.format(new Date()) + "ERROR: " + message + " ===>>> \n" + sw.toString();
        System.err.println(exceptionLog);
    }

    public static void printError(String message){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
        String exceptionLog = dateFormat.format(new Date()) + "ERROR: " + message;
        System.err.println(exceptionLog);
    }

    public static void printWarning(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
        String exceptionText = dateFormat.format(new Date()) + "WARNING: " + message;
        System.out.println(exceptionText);
    }
}
