package propconfig;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;

    public ConfigReader(String filePath) {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(filePath); // passing config path to contructor
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Failed to load configuration from: " + filePath);
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}