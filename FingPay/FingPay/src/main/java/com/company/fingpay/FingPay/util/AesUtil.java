package com.company.fingpay.FingPay.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public final class AesUtil {

    private static final String AES = "AES";
    private static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private AesUtil() {}

    /* -------------------------------
        GENERATE AES SESSION KEY
    -------------------------------- */

    public static String generateSessionKey() throws Exception {

        KeyGenerator keyGen = KeyGenerator.getInstance(AES);

        keyGen.init(128);

        SecretKey key = keyGen.generateKey();

        return Base64.getEncoder()
                .encodeToString(key.getEncoded());
    }

    /* -------------------------------
        AES ENCRYPT
    -------------------------------- */

    public static String encrypt(String sessionKey, String data) throws Exception {

        SecretKeySpec keySpec = getKey(sessionKey);

        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] encrypted =
                cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[iv.length + encrypted.length];

        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    /* -------------------------------
        AES DECRYPT
    -------------------------------- */

    public static String decrypt(String sessionKey, String encryptedData) throws Exception {

        byte[] decoded =
                Base64.getDecoder().decode(encryptedData);

        byte[] iv = new byte[16];
        byte[] encrypted = new byte[decoded.length - 16];

        System.arraycopy(decoded, 0, iv, 0, 16);
        System.arraycopy(decoded, 16, encrypted, 0, encrypted.length);

        SecretKeySpec keySpec = getKey(sessionKey);

        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);

        cipher.init(
                Cipher.DECRYPT_MODE,
                keySpec,
                new IvParameterSpec(iv)
        );

        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /* -------------------------------
        CREATE SECRET KEY
    -------------------------------- */

    private static SecretKeySpec getKey(String sessionKey) {

        byte[] decodedKey =
                Base64.getDecoder().decode(sessionKey);

        return new SecretKeySpec(decodedKey, AES);
    }
}