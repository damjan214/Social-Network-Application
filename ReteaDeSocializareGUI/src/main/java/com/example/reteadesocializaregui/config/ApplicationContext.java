package com.example.reteadesocializaregui.config;

import java.util.Properties;

import static com.example.reteadesocializaregui.config.Config.getProperties;

public class ApplicationContext {
    private static final Properties PROPERTIES = getProperties();

    public static Properties getPROPERTIES() {
        return PROPERTIES;
    }
}
