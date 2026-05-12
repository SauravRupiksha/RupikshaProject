package com.rupiksha.fingpayaeps.faeps.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupiksha.fingpayaeps.faeps.dto.*;
import com.rupiksha.fingpayaeps.faeps.util.AadhaarValidator;
import com.rupiksha.fingpayaeps.faeps.util.EncryptionUtil;
import com.rupiksha.fingpayaeps.faeps.util.PidXmlParser;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BiometricService {

    private static final Logger log = LoggerFactory.getLogger(BiometricService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Value("${fingpay.ekyc.biometric-url}")
    private String biometricUrl;

    @Value("${fingpay.supermerchant.id}")
    private int superMerchantId;

    @Value("${device.imei}")
    private String deviceImei; // 🔥 FIXED

    public BiometricResponseDTO<Object> processBiometric(@Valid BiometricFrontendRequest request) {

        String rawResponse = null;

        try {
            // =========================
            // 🔐 Aadhaar Validation
            // =========================
            if (!AadhaarValidator.isValid(request.getAadhaarNumber())) {
                return BiometricResponseDTO.error("Invalid Aadhaar", "VALIDATION", null);
            }

            // =========================
            // 🔥 PID XML PARSE
            // =========================
            CaptureResponse capture = PidXmlParser.parse(request.getPidXml());

            // =========================
            // 🔥 CARD BUILD
            // =========================
            P2CardnumberORUID card = new P2CardnumberORUID();
            card.setAdhaarNumber(request.getAadhaarNumber());
            card.setIndicatorforUID(Integer.parseInt(request.getIndicatorforUID()));
            card.setNationalBankIdentificationNumber(request.getNationalBankIdentificationNumber());

            // =========================
            // 🔥 FINAL DTO
            // =========================
            BiometricRequestDTO finalReq = BiometricRequestDTO.builder()
                    .merchantLoginId(request.getMerchantLoginId())
                    .superMerchantId(superMerchantId)
                    .primaryKeyId(request.getPrimaryKeyId())
                    .encodeFPTxnId(request.getEncodeFPTxnId())
                    .requestRemarks("ekyc")
                    .cardnumberORUID(card)
                    .captureResponse(capture)
                    .build();

            String json = mapper.writeValueAsString(finalReq);

            // 🔒 SAFE LOG (masked Aadhaar)
            log.info("Request Aadhaar: {}", AadhaarValidator.mask(request.getAadhaarNumber()));

            // =========================
            // 🔐 ENCRYPT
            // =========================
            EncryptionUtil.EncryptionResult enc = EncryptionUtil.encryptRequest(json);

            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.set("trnTimestamp", timestamp);
            headers.set("hash", enc.getHash());
            headers.set("deviceIMEI", deviceImei);
            headers.set("eskey", enc.getEskey());

            HttpEntity<String> entity = new HttpEntity<>(enc.getBody(), headers);

            log.info("➡️ Calling Fingpay API");

            // =========================
            // 🚀 API CALL
            // =========================
            ResponseEntity<String> response = restTemplate.exchange(
                    biometricUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            rawResponse = response.getBody();

            if (rawResponse == null || rawResponse.isEmpty()) {
                return BiometricResponseDTO.error("Empty response from Fingpay", "EMPTY", null);
            }

            // =========================
            // 🔐 DECRYPT
            // =========================
            String decrypted = rawResponse.trim().startsWith("{")
                    ? rawResponse
                    : EncryptionUtil.decryptResponse(rawResponse, enc.getKey());

            log.info("Response StatusCode: {}", mapper.readTree(decrypted).get("statusCode"));

            // =========================
            // ✅ PARSE RESPONSE
            // =========================
            FingpayResponseDTO fingpay =
                    mapper.readValue(decrypted, FingpayResponseDTO.class);

            if (fingpay.isStatus() && fingpay.getStatusCode() == 10000) {
                return BiometricResponseDTO.success(null, fingpay.getMessage(), decrypted);
            } else {
                return BiometricResponseDTO.error(
                        fingpay.getMessage(),
                        String.valueOf(fingpay.getStatusCode()),
                        decrypted
                );
            }

        } catch (Exception e) {
            log.error("❌ Biometric EKYC Error", e);

            return BiometricResponseDTO.error(
                    "Biometric EKYC Failed",
                    "EXCEPTION",
                    rawResponse
            );
        }
    }
}