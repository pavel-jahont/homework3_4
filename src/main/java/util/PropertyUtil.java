package util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertyUtil {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final String CONFIG_PROPERTY_FILE_LOCATION = "config.properties";

    private Properties properties;

    public String getProperty(String name) {
        this.properties = new Properties();
        try {
            InputStream propertiesStream = getClass().getClassLoader().getResourceAsStream(CONFIG_PROPERTY_FILE_LOCATION);
            properties.load(propertiesStream);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            throw new IllegalArgumentException("Config property file is not found");
        }
        return properties.getProperty(name);
    }
}