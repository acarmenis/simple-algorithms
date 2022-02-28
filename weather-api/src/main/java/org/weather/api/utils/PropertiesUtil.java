package org.weather.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertiesUtil
 */
public class PropertiesUtil {
    /**
     * Loads from app properties file app's properties
     * @param fileName: the prop file name
     * @return Properties
     * @throws IOException
     */
    public static Properties loadProperties(String fileName) throws IOException {
        Properties properties = null;
        // at this try body, id does not have any catch simply because any resource opened is automatically closed.
        // as for any exception which may occur, is restricted and thrown from the method's signature.
        try (InputStream iStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            properties = new Properties();
            properties.load(iStream);
        }
        // return the properties
        return properties;
    }
}
