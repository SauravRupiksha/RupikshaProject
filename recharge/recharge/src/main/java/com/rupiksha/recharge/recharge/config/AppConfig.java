package com.rupiksha.recharge.recharge.config;

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

        // CONNECTION TIMEOUT
        factory.setConnectTimeout(15000);

        // RESPONSE TIMEOUT
        factory.setReadTimeout(15000);

        return new RestTemplate(factory);
    }
}