package com.company.fingpay.FingPay.util;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public final class HashUtil {

    private static final String SHA256 = "SHA-256";

    private HashUtil() {
        // prevent object creation
    }

    /* -------------------------------
        GENERATE SHA256 HASH
    -------------------------------- */

    public static String generateHash(String data) throws Exception {
        return sha256(data);
    }

    /* -------------------------------
        STATUS CHECK HASH
        tranId + "+" + MD5(password)
    -------------------------------- */

    public static String generateStatusHash(
            String tranId,
            String md5Password) throws Exception {

        String data = tranId + "+" + md5Password;

        return sha256(data);
    }

    /* -------------------------------
        RECON HASH
        merchantLoginId + "+" + fromDate + "+" + toDate
    -------------------------------- */

    public static String generateReconHash(
            String merchantLoginId,
            String fromDate,
            String toDate) throws Exception {

        String data =
                merchantLoginId + "+" + fromDate + "+" + toDate;

        return sha256(data);
    }

    /* -------------------------------
        CORE SHA256 METHOD
    -------------------------------- */

    private static String sha256(String data) throws Exception {

        MessageDigest digest =
                MessageDigest.getInstance(SHA256);

        byte[] hash =
                digest.digest(
                        data.getBytes(StandardCharsets.UTF_8)
                );

        return Base64.getEncoder()
                .encodeToString(hash);
    }

}
