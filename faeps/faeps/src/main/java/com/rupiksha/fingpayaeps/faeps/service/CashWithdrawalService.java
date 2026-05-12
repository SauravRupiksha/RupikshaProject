package com.rupiksha.fingpayaeps.faeps.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupiksha.fingpayaeps.faeps.dto.*;

import com.rupiksha.fingpayaeps.faeps.exception.CashWithdrawalException;
import com.rupiksha.fingpayaeps.faeps.util.EncryptionUtil;
import com.rupiksha.fingpayaeps.faeps.util.PidXmlParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashWithdrawalService {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    @Value("${fingpay.aeps.withdrawal-url}")
    private String url;

    @Value("${device.imei}")
    private String imei;

    public CashWithdrawalResponse withdraw(CashWithdrawalRequest request) {

        String rawResponse;

        try {
            // ================= PID PARSE =================
            CaptureResponse capture = PidXmlParser.parse(request.getPidXml());

            if (!"0".equals(capture.getErrCode())) {
                throw new CashWithdrawalException(
                        "Biometric capture failed",
                        "BIO_ERROR",
                        request.getMerchantTranId()
                );
            }

            request.setCaptureResponse(capture);

            // ================= PIN HASH =================
            request.setMerchantPin(md5(request.getMerchantPin()));

            // ================= TIMESTAMP =================
            String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            request.setTimestamp(timestamp);

            // ================= JSON =================
            String json = mapper.writeValueAsString(request);

            // ================= ENCRYPT =================
            EncryptionUtil.EncryptionResult enc = EncryptionUtil.encryptRequest(json);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.set("trnTimestamp", timestamp);
            headers.set("hash", enc.getHash());
            headers.set("deviceIMEI", imei);
            headers.set("eskey", enc.getEskey());

            HttpEntity<String> entity = new HttpEntity<>(enc.getBody(), headers);

            // ================= API CALL =================
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            rawResponse = response.getBody();

            if (rawResponse == null || rawResponse.isEmpty()) {
                throw new CashWithdrawalException(
                        "Empty response from Fingpay",
                        "EMPTY",
                        request.getMerchantTranId()
                );
            }

            // ================= DECRYPT =================
            String decrypted = rawResponse.trim().startsWith("{")
                    ? rawResponse
                    : EncryptionUtil.decryptResponse(rawResponse, enc.getKey());

            CashWithdrawalResponse res =
                    mapper.readValue(decrypted, CashWithdrawalResponse.class);

            // ================= SUCCESS CHECK =================
            if (!isSuccess(res)) {
                throw new CashWithdrawalException(
                        "Transaction Failed",
                        res.getData() != null ? res.getData().getResponseCode() : "NA",
                        request.getMerchantTranId()
                );
            }

            return res;

        } catch (CashWithdrawalException e) {
            throw e;
        } catch (Exception e) {
            log.error("❌ Withdrawal Error", e);
            throw new CashWithdrawalException(
                    "Internal error during withdrawal",
                    "INTERNAL_ERROR",
                    request.getMerchantTranId()
            );
        }
    }

    private boolean isSuccess(CashWithdrawalResponse res) {

        if (res == null || res.getData() == null) return false;

        CashWithdrawalData d = res.getData();

        return res.isStatus()
                && "SUCCESS".equalsIgnoreCase(d.getTransactionStatus())
                && "00".equals(d.getResponseCode())
                && d.getBankRRN() != null;
    }

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("MD5 failed", e);
        }
    }
}