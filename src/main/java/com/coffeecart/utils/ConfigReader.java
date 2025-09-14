package com.coffeecart.utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class ConfigReader {
    private static final Properties props = new Properties();
    static {
        try (FileInputStream fis = new FileInputStream("resources/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }
    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
