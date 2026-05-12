package com.rupiksha.fingpayaeps.faeps.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.rupiksha.fingpayaeps.faeps.dto.TwoFaRequest;
import com.rupiksha.fingpayaeps.faeps.dto.TwoFaResponse;
import com.rupiksha.fingpayaeps.faeps.dto.TwoFaCaptureResponse;
import com.rupiksha.fingpayaeps.faeps.util.TwoFaEncryptionUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwoFaService {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    @Value("${fingpay.aeps.tfa-url}")
    private String url;

    @Value("${fingpay.secret.key}")
    private String secretKey;

    @Value("${device.imei}")
    private String deviceImei;

    public TwoFaResponse do2FA(TwoFaRequest request) {

        try {
            log.info("🚀 2FA START txnId={}", request.getMerchantTranId());

            // ================= DEFAULT =================
            request.setTransactionType("AUO");
            request.setServiceType("AEPS");

            // ================= PIN =================
            String pin = request.getMerchantPin();

            if (pin == null || pin.trim().isEmpty()) {
                throw new IllegalArgumentException("merchantPin required");
            }

            pin = pin.trim();

            if (!pin.matches("[a-fA-F0-9]{32}")) {
                request.setMerchantPin(md5(pin));
            }

            // ================= CAPTURE VALIDATION =================
            TwoFaCaptureResponse capture = request.getCaptureResponse();

            if (capture == null) {
                throw new IllegalArgumentException("captureResponse missing");
            }

            if (capture.getPiddata() == null || capture.getPiddata().isBlank()) {
                throw new IllegalArgumentException("PID data missing");
            }

            if (capture.getHmac() == null || capture.getHmac().isBlank()) {
                throw new IllegalArgumentException("HMAC missing");
            }

            if (capture.getSessionKey() == null || capture.getSessionKey().isBlank()) {
                throw new IllegalArgumentException("SessionKey missing");
            }

            log.info("📦 PID LENGTH => {}", capture.getPiddata().length());

            // ================= JSON (🔥 FINAL FIX) =================
            ObjectMapper localMapper = mapper.copy();

            // ❗ DO NOT SORT (IMPORTANT)
            localMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
            localMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            // ❌ NO TRIM (CRITICAL)
            String json = localMapper.writeValueAsString(request);

            log.info("📦 FINAL JSON => {}", json);
            log.info("📦 JSON LENGTH => {}", json.length());

            // ================= ENCRYPT =================
            TwoFaEncryptionUtil.EncryptionResult enc =
                    TwoFaEncryptionUtil.encryptRequest(json, secretKey);

            // 🔥 DEBUG (VERY IMPORTANT)
            log.info("🔥 HASH INPUT => {}", json + secretKey + enc.getTimestamp());

            // ================= HEADERS =================
            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            headers.set("trnTimestamp", enc.getTimestamp());
            headers.set("hash", enc.getHash());
            headers.set("deviceIMEI", deviceImei.trim());
            headers.set("eskey", enc.getEskey());

            HttpEntity<String> entity = new HttpEntity<>(enc.getBody(), headers);

            log.info("📤 REQUEST SENT");

            // ================= CALL =================
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            String raw = response.getBody();

            log.info("📥 RESPONSE => {}", raw);

            TwoFaResponse res = localMapper.readValue(raw, TwoFaResponse.class);

            if (res == null) {
                throw new RuntimeException("Invalid response");
            }

            String code = res.safeResponseCode();

            if ("00".equals(code)) {
                log.info("🎉 SUCCESS");
            } else if ("FP069".equals(code)) {
                log.warn("⚠️ 2FA REQUIRED");
            } else {
                throw new RuntimeException("❌ Failed: " + res.safeResponseMessage());
            }

            return res;

        } catch (Exception e) {
            log.error("❌ 2FA ERROR", e);
            throw new RuntimeException("2FA failed: " + e.getMessage(), e);
        }
    }

    // ================= MD5 =================
    private String md5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("MD5 failed", e);
        }
    }
}