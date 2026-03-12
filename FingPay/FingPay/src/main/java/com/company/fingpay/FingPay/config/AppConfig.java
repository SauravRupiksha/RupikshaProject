package com.company.fingpay.FingPay.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {

        // Connection Pool Manager
        PoolingHttpClientConnectionManager connectionManager =
                new PoolingHttpClientConnectionManager();

        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(20);

        // HttpClient
        CloseableHttpClient httpClient =
                HttpClients.custom()
                        .setConnectionManager(connectionManager)
                        .build();

        // Request Factory
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        factory.setReadTimeout(Duration.ofSeconds(10));
        factory.setConnectionRequestTimeout(Duration.ofSeconds(30));

        return new RestTemplate(factory);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}