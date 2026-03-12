package com.company.fingpay.FingPay.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;

public final class HashUtil {

    private static final String SHA256 = "SHA-256";

    private HashUtil() {}

    /* -------------------------------
        GENERATE SHA256 HASH
    -------------------------------- */

    public static String generateHash(String data) {

        Objects.requireNonNull(data, "Hash data cannot be null");

        return sha256(data.trim());
    }

    /* -------------------------------
        STATUS CHECK HASH
        tranId + "+" + MD5(password)
    -------------------------------- */

    public static String generateStatusHash(
            String tranId,
            String md5Password) {

        Objects.requireNonNull(tranId);
        Objects.requireNonNull(md5Password);

        String data = tranId.trim() + "+" + md5Password.trim();

        return sha256(data);
    }

    /* -------------------------------
        RECON HASH
        merchantLoginId + "+" + fromDate + "+" + toDate
    -------------------------------- */

    public static String generateReconHash(
            String merchantLoginId,
            String fromDate,
            String toDate) {

        String data =
                merchantLoginId.trim() +
                        "+" +
                        fromDate.trim() +
                        "+" +
                        toDate.trim();

        return sha256(data);
    }

    /* -------------------------------
        CORE SHA256 METHOD
    -------------------------------- */

    private static String sha256(String data) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance(SHA256);

            byte[] hash =
                    digest.digest(
                            data.getBytes(StandardCharsets.UTF_8)
                    );

            return Base64.getEncoder()
                    .encodeToString(hash);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Hash generation failed", e);
        }
    }
}