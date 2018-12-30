package com.jurijz.geocodingsearch.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jurijz on 10/21/2018.
 */
public class PropertyUtil {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyUtil.class);

    private static final String PROPERTIES_FILENAME = "app.properties";

    private static Properties prop;

    public static void initProperties() {
        prop = new Properties();

        try (InputStream is = PropertyUtil.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILENAME)) {

            if (is == null) {
                return;
            }

            prop.load(is);
            LOG.info("Properties loaded.");
        } catch (IOException ex) {
            LOG.error("Cannot load properties.");
            ex.printStackTrace();
        }
    }

    public static String getProperty(String propertyName) {
        return prop.get(propertyName).toString();
    }
}
