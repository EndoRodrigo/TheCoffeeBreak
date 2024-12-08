package org.endorodrigo.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private Properties properties;

    public Config(String path) throws IOException {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
