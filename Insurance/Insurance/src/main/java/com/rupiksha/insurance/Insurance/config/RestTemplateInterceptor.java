package com.rupiksha.insurance.Insurance.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger =
            LoggerFactory.getLogger(RestTemplateInterceptor.class);

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {

        long startTime = System.currentTimeMillis();

        logRequest(request, body);

        ClientHttpResponse response = execution.execute(request, body);

        logResponse(response, startTime);

        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        logger.info("➡️ URI: {}", request.getURI());
        logger.info("➡️ Method: {}", request.getMethod());

        if (body.length > 0) {
            logger.debug("➡️ Body: {}", new String(body, StandardCharsets.UTF_8));
        }
    }

    private void logResponse(ClientHttpResponse response, long startTime) throws IOException {

        long timeTaken = System.currentTimeMillis() - startTime;

        String body = new BufferedReader(
                new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))
                .lines()
                .reduce("", (a, b) -> a + b);

        logger.info("⬅️ Status: {}", response.getStatusCode());
        logger.info("⏱ Time Taken: {} ms", timeTaken);

        if (body.length() > 500) {
            logger.debug("⬅️ Body: {}...", body.substring(0, 500));
        } else {
            logger.debug("⬅️ Body: {}", body);
        }
    }
}