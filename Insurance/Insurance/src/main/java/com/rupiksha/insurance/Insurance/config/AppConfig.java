package com.rupiksha.insurance.Insurance.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class AppConfig {

    @Value("${venus.api.connect-timeout}")
    private int connectTimeout;

    @Value("${venus.api.read-timeout}")
    private int readTimeout;

    @Autowired
    private RestTemplateInterceptor interceptor;

    @Bean
    public RestTemplate restTemplate() {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);

        BufferingClientHttpRequestFactory bufferingFactory =
                new BufferingClientHttpRequestFactory(factory);

        RestTemplate restTemplate = new RestTemplate(bufferingFactory);

        restTemplate.setInterceptors(
                Collections.singletonList(interceptor)
        );

        return restTemplate;
    }
}