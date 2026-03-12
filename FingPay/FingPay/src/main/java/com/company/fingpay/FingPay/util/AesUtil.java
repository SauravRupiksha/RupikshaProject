package com.company.fingpay.FingPay.util;



import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class AesUtil {

    private static final String AES = "AES";
    private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";

    private AesUtil() {
        // prevent object creation
    }

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

        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        byte[] encrypted =
                cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder()
                .encodeToString(encrypted);
    }

    /* -------------------------------
        AES DECRYPT (optional)
    -------------------------------- */

    public static String decrypt(String sessionKey, String encryptedData) throws Exception {

        SecretKeySpec keySpec = getKey(sessionKey);

        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);

        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        byte[] decrypted =
                cipher.doFinal(Base64.getDecoder().decode(encryptedData));

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
