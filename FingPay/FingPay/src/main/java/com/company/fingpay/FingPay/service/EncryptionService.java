package com.company.fingpay.FingPay.service;


import com.company.fingpay.FingPay.util.AesUtil;
import com.company.fingpay.FingPay.util.HashUtil;
import com.company.fingpay.FingPay.util.LoggerUtil;
import com.company.fingpay.FingPay.util.RsaUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EncryptionService {

    private static final Logger logger =
            LoggerUtil.getLogger(EncryptionService.class);

    @Value("${fingpay.security.key}")
    private String securityKey;

    private final RsaUtil rsaUtil;

    public EncryptionService(RsaUtil rsaUtil) {
        this.rsaUtil = rsaUtil;
    }

    public Map<String,String> encryptRequest(String json) {

        Map<String,String> result = new HashMap<>();

        try {

            String sessionKey = AesUtil.generateSessionKey();

            String encryptedBody =
                    AesUtil.encrypt(sessionKey, json);

            // FIX
            String eskey =
                    rsaUtil.encrypt(sessionKey);

            String hash =
                    HashUtil.generateHash(json + securityKey);

            result.put("hash", hash);
            result.put("eskey", eskey);
            result.put("body", encryptedBody);

        } catch (Exception e) {

            logger.error("Encryption Failed", e);
            throw new RuntimeException("Encryption Error");
        }

        return result;
    }
}