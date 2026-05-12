package com.rupiksha.payout.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "levin")
public class LevinConfig {

    private String baseUrl;
    private String userId;
    private String apiToken;

}