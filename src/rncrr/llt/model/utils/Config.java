package rncrr.llt.model.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Config extends Properties {

    private static Properties properties;

    private static synchronized Properties createConfig() {
        if (properties == null)
            properties = new Config();
        return properties;
    }

    /**
     * Загружаем конфигурационный файл
     * @param configFile - конфугурационный файл
     * @return все свойства конфига
     */
    public static Properties loadConfig(String configFile) throws IOException {
        FileInputStream is = new FileInputStream(configFile);
        if (properties == null) {
            createConfig();
            properties.load(is);
        } else {
            properties.load(is);
        }
        return properties;
    }

    /**
     * Метод возвращает строковое значение по ключу, если значение null
     * подставляется значение поумолчанию
     * @param key - ключ
     * @param defaultValue - значение поусолчанию
     * @return value - значение по ключу
     */
    public static String getStringProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Метод возвращает строковое значение по ключу
     * @param key - ключ
     * @return value - значенипо ключу
     */
    public static String getStringProperty(String key) {
        return getStringProperty(key, "");
    }

    /**
     * Возвращает числовое значение по ключу, если значение null возвращается
     * значение по умолчанию
     * @param key - ключ
     * @param defaultValue - значение поумолчанию
     * @return intVal - значение типа int
     */
    public static int getIntProperty(String key, int defaultValue) {
        int intVal = defaultValue;
        String propValue = getStringProperty(key);
        if (propValue != null)
            intVal = Integer.parseInt(propValue);
        return intVal;
    }

    /**
     * Возвращает double значение по ключу, если значение null возвращается
     * @param key - ключ
     * @param defaultValue - значение по умолчанию
     * @return doubleVal - значение типа double
     */
    public static double getDoubleProperty(String key, double defaultValue) {
        double doubleVal = defaultValue;
        String propValue = getStringProperty(key);
        if (propValue != null)
            doubleVal = Double.parseDouble(propValue);
        return doubleVal;
    }

    /**
     * Возвращает значение true or false по ключу, если значение null
     * возвращается значение поумолчанию - true
     * @param key - ключ
     * @param defaultValue - значение по умолчанию
     * @return true or false
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        boolean boolVal = defaultValue;
        String propValue = getStringProperty(key);
        if ((propValue != null) && (propValue.equalsIgnoreCase("true")))
            boolVal = true;
        return boolVal;
    }

    public static boolean isProperties(String key) {
        return !getStringProperty(key).equals("");
    }

}