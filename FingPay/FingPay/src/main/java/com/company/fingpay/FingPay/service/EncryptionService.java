package com.company.fingpay.FingPay.service;




import com.company.fingpay.FingPay.util.AesUtil;
import com.company.fingpay.FingPay.util.HashUtil;
import com.company.fingpay.FingPay.util.LoggerUtil;
import com.company.fingpay.FingPay.util.RsaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EncryptionService {

    @Value("${fingpay.security.key}")
    private String securityKey;

    public Map<String,String> encryptRequest(String json) throws Exception {

        Map<String,String> result = new HashMap<>();

        try {

            /* ---------------------------
               AES SESSION KEY GENERATION
            ---------------------------- */
            String sessionKey = AesUtil.generateSessionKey();

            /* ---------------------------
               AES BODY ENCRYPTION
            ---------------------------- */
            String encryptedBody =
                    AesUtil.encrypt(sessionKey, json);

            /* ---------------------------
               RSA SESSION KEY ENCRYPTION
            ---------------------------- */
            String eskey =
                    RsaUtil.encrypt(sessionKey);

            /* ---------------------------
               HASH GENERATION
            ---------------------------- */
            String hash =
                    HashUtil.generateHash(json + securityKey);

            result.put("hash", hash);
            result.put("eskey", eskey);
            result.put("body", encryptedBody);

        } catch (Exception e) {

            LoggerUtil.logger.error("Encryption Failed : " + e.getMessage());
            throw new RuntimeException("Encryption Error");

        }

        return result;
    }
}
