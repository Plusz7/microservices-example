package org.java.license.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "example")
public class ServiceConfig {

    private String property;

    public String getProperty() {
        return property;
    }
}
