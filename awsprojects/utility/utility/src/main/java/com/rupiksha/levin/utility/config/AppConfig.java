package com.rupiksha.levin.utility.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {

        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();

        // Connection timeout
        factory.setConnectTimeout(30000);

        // Response timeout
        factory.setReadTimeout(30000);

        return new RestTemplate(factory);
    }
}