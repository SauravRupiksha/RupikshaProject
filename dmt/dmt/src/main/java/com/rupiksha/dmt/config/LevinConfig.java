package com.rupiksha.dmt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "levin")
public class LevinConfig {

    private String baseUrl;
    private String apiToken;
    private String userId;

}