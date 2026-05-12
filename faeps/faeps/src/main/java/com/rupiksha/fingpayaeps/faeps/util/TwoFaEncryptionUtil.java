package com.rupiksha.fingpayaeps.faeps.util;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
public class TwoFaEncryptionUtil {

    private static RSAPublicKey PUBLIC_KEY;

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());

            try (InputStream is = TwoFaEncryptionUtil.class
                    .getClassLoader()
                    .getResourceAsStream("fingpay_public.cer")) {

                if (is == null) {
                    throw new RuntimeException("❌ fingpay_public.cer not found");
                }

                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
                PUBLIC_KEY = (RSAPublicKey) cert.getPublicKey();
            }

        } catch (Exception e) {
            throw new RuntimeException("❌ Public key load failed", e);
        }
    }

    // ================= TIMESTAMP =================
    private static String generateTimestamp() {
        return ZonedDateTime.now(ZoneId.of("Asia/Kolkata"))
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
    // ================= HASH =================
    private static String generateHash(String json, String secretKey, String timestamp) {
        try {
            // ✅ Correct order
            String raw = json + timestamp + secretKey;
            System.out.println("HASH INPUT => " + (json + timestamp + secretKey));

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);

        } catch (Exception e) {
            throw new RuntimeException("❌ Hash generation failed", e);
        }
    }
    // ================= SESSION KEY =================
    private static byte[] generateSessionKey() {
        byte[] key = new byte[16];
        new SecureRandom().nextBytes(key);
        return key;
    }

    // ================= AES ENCRYPT =================
    private static String encryptUsingSessionKey(byte[] skey, byte[] data) {
        try {
            PaddedBufferedBlockCipher cipher =
                    new PaddedBufferedBlockCipher(new AESEngine(), new PKCS7Padding());

            cipher.init(true, new KeyParameter(skey));

            byte[] temp = new byte[cipher.getOutputSize(data.length)];

            int len1 = cipher.processBytes(data, 0, data.length, temp, 0);
            int len2 = cipher.doFinal(temp, len1);

            byte[] result = Arrays.copyOf(temp, len1 + len2);

            return Base64.getEncoder().encodeToString(result);

        } catch (Exception e) {
            throw new RuntimeException("❌ AES encryption failed", e);
        }
    }

    // ================= RSA ENCRYPT =================
    private static String encryptUsingPublicKey(byte[] message) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

            cipher.init(Cipher.ENCRYPT_MODE, PUBLIC_KEY, new SecureRandom());

            byte[] encrypted = cipher.doFinal(message);

            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new RuntimeException("❌ RSA encryption failed", e);
        }
    }

    // ================= FINAL =================
    public static EncryptionResult encryptRequest(String json, String secretKey) {

        if (json == null || json.isBlank()) {
            throw new RuntimeException("❌ JSON empty");
        }

        // ❗ DO NOT MODIFY JSON (important for hash)
        String timestamp = generateTimestamp();

        byte[] sessionKey = generateSessionKey();

        // 🔐 BODY
        String encryptedBody = encryptUsingSessionKey(
                sessionKey,
                json.getBytes(StandardCharsets.UTF_8)
        );

        // 🔐 HASH
        String hash = generateHash(json, secretKey.trim(), timestamp);

        // 🔐 ESKEY
        String eskey = encryptUsingPublicKey(sessionKey);

        // 🔥 DEBUG (CRITICAL)
        log.info("🔥 HASH INPUT => {}", json + secretKey.trim() + timestamp);
        log.info("🔐 HASH => {}", hash);
        log.info("📦 BODY LENGTH => {}", encryptedBody.length());
        log.info("🔑 ESKEY LENGTH => {}", eskey.length());
        log.info("⏱️ TIMESTAMP => {}", timestamp);

        return new EncryptionResult(hash, eskey, encryptedBody, timestamp);
    }

    // ================= RESULT =================
    public static class EncryptionResult {

        private final String hash;
        private final String eskey;
        private final String body;
        private final String timestamp;

        public EncryptionResult(String hash, String eskey, String body, String timestamp) {
            this.hash = hash;
            this.eskey = eskey;
            this.body = body;
            this.timestamp = timestamp;
        }

        public String getHash() { return hash; }
        public String getEskey() { return eskey; }
        public String getBody() { return body; }
        public String getTimestamp() { return timestamp; }
    }
}