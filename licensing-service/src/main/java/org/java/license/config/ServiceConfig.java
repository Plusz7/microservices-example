package org.java.license.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "example")
@Getter @Setter
public class ServiceConfig {

    private String property;
}
