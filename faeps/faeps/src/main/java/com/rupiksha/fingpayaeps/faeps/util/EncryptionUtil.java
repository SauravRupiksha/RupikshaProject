package com.rupiksha.fingpayaeps.faeps.util;

import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class EncryptionUtil {

    private static final Logger log = LoggerFactory.getLogger(EncryptionUtil.class);

    private static final String PROVIDER = "BC";
    private static final String RSA_ALGO = "RSA/ECB/PKCS1Padding";

    private static RSAPublicKey PUBLIC_KEY;

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());

            InputStream is = new ClassPathResource("fingpay_public.cer").getInputStream();
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
            PUBLIC_KEY = (RSAPublicKey) cert.getPublicKey();

            log.info("✅ Public Key Loaded");

        } catch (Exception e) {
            throw new RuntimeException("Public key load failed", e);
        }
    }

    // 🔐 AES KEY
    public static SecretKey generateSessionKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES", PROVIDER);
            keyGen.init(128);
            return keyGen.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 🔐 HASH
    public static String generateHash(String json) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256", PROVIDER);
            byte[] hash = digest.digest(json.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 🔐 AES ENCRYPT (FIXED)
    public static String encryptBody(String json, SecretKey key) {
        try {
            byte[] data = json.getBytes(StandardCharsets.UTF_8);

            PaddedBufferedBlockCipher cipher =
                    new PaddedBufferedBlockCipher(new AESEngine(), new PKCS7Padding());

            cipher.init(true, new KeyParameter(key.getEncoded()));

            byte[] output = new byte[cipher.getOutputSize(data.length)];

            int len1 = cipher.processBytes(data, 0, data.length, output, 0);
            int len2 = cipher.doFinal(output, len1);

            byte[] encrypted = new byte[len1 + len2];
            System.arraycopy(output, 0, encrypted, 0, encrypted.length);

            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new RuntimeException("AES encryption failed", e);
        }
    }

    // 🔐 RSA ENCRYPT
    public static String generateEskey(SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGO, PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, PUBLIC_KEY, new SecureRandom());

            byte[] encrypted = cipher.doFinal(key.getEncoded());

            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new RuntimeException("RSA encryption failed", e);
        }
    }

    // 🔐 AES DECRYPT (🔥 IMPORTANT)
    public static String decryptResponse(String encrypted, SecretKey key) {
        try {
            byte[] data = Base64.getDecoder().decode(encrypted);

            PaddedBufferedBlockCipher cipher =
                    new PaddedBufferedBlockCipher(new AESEngine(), new PKCS7Padding());

            cipher.init(false, new KeyParameter(key.getEncoded()));

            byte[] output = new byte[cipher.getOutputSize(data.length)];

            int len1 = cipher.processBytes(data, 0, data.length, output, 0);
            int len2 = cipher.doFinal(output, len1);

            byte[] decrypted = new byte[len1 + len2];
            System.arraycopy(output, 0, decrypted, 0, decrypted.length);

            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("AES decryption failed", e);
        }
    }

    // 🔥 FINAL FLOW
    public static EncryptionResult encryptRequest(String json) {

        SecretKey sessionKey = generateSessionKey();

        return new EncryptionResult(
                generateHash(json),
                generateEskey(sessionKey),
                encryptBody(json, sessionKey),
                sessionKey
        );
    }

    // 🔥 RESULT HOLDER
    public static class EncryptionResult {
        private final String hash;
        private final String eskey;
        private final String body;
        private final SecretKey key;

        public EncryptionResult(String hash, String eskey, String body, SecretKey key) {
            this.hash = hash;
            this.eskey = eskey;
            this.body = body;
            this.key = key;
        }

        public String getHash() { return hash; }
        public String getEskey() { return eskey; }
        public String getBody() { return body; }
        public SecretKey getKey() { return key; }
    }
}