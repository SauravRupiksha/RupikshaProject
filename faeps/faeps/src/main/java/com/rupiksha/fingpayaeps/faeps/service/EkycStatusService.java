package com.rupiksha.fingpayaeps.faeps.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupiksha.fingpayaeps.faeps.dto.EkycStatusRequest;
import com.rupiksha.fingpayaeps.faeps.dto.EkycStatusResponseDTO;
import com.rupiksha.fingpayaeps.faeps.dto.FingpayResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Service
public class EkycStatusService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${fingpay.secret.key}")
    private String secretKey;

    @Value("${fingpay.ekyc.status-url}")
    private String statusUrl;

    public EkycStatusResponseDTO checkStatus(EkycStatusRequest request) {

        String rawResponse = null;

        try {
            // =========================
            // ✅ JSON
            // =========================
            String json = mapper.writeValueAsString(request);

            // =========================
            // ⏱️ TIMESTAMP
            // =========================
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                    .format(new Date());

            // =========================
            // 🔐 HASH
            // =========================
            String finalString = json + secretKey + timestamp;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(finalString.getBytes(StandardCharsets.UTF_8));

            String hash = Base64.getEncoder().encodeToString(hashBytes);

            // =========================
            // 📦 HEADERS
            // =========================
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("trnTimestamp", timestamp);
            headers.set("hash", hash);

            HttpEntity<String> entity = new HttpEntity<>(json, headers);

            // =========================
            // 🚀 API CALL
            // =========================
            ResponseEntity<String> response = restTemplate.exchange(
                    statusUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            rawResponse = response.getBody();

            if (rawResponse == null || rawResponse.isEmpty()) {
                return EkycStatusResponseDTO.error("Empty response", "EMPTY", null);
            }

            // =========================
            // ✅ PARSE RESPONSE
            // =========================
            FingpayResponseDTO fingpay =
                    mapper.readValue(rawResponse, FingpayResponseDTO.class);

            // =========================
            // 🔥 STATUS MAPPING
            // =========================
            String kycStatus;

            if (fingpay.isStatus() && fingpay.getStatusCode() == 10000) {
                kycStatus = "COMPLETED";
            } else if (fingpay.getStatusCode() == 10005) {
                kycStatus = "FAILED";
            } else {
                kycStatus = "PENDING";
            }

            // =========================
            // 🎯 FINAL RESPONSE
            // =========================
            if (fingpay.isStatus()) {
                return EkycStatusResponseDTO.success(
                        fingpay.getMessage(),
                        kycStatus,
                        fingpay.getStatusCode(),
                        fingpay.getData(),
                        rawResponse
                );
            } else {
                return EkycStatusResponseDTO.error(
                        fingpay.getMessage(),
                        String.valueOf(fingpay.getStatusCode()),
                        rawResponse
                );
            }

        } catch (Exception e) {
            return EkycStatusResponseDTO.error(
                    "Status Check Failed",
                    "EXCEPTION",
                    rawResponse
            );
        }
    }
}