package com.company.fingpay.FingPay.util;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

@Component
public class RsaUtil {

    private static final Logger logger =
            LoggerFactory.getLogger(RsaUtil.class);

    private static final String CERT_TYPE = "X.509";
    private static final String RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    @Value("${fingpay.public.cert}")
    private String certificate;

    private PublicKey publicKey;

    /* -------------------------------
        LOAD CERTIFICATE
    -------------------------------- */

    @PostConstruct
    public void loadCertificate() {

        try {

            logger.info("Loading Fingpay public certificate");

            String formattedCert = certificate;

            if(!certificate.contains("BEGIN CERTIFICATE")){
                formattedCert =
                        "-----BEGIN CERTIFICATE-----\n" +
                                certificate +
                                "\n-----END CERTIFICATE-----";
            }

            CertificateFactory factory =
                    CertificateFactory.getInstance(CERT_TYPE);

            ByteArrayInputStream stream =
                    new ByteArrayInputStream(
                            formattedCert.getBytes(StandardCharsets.UTF_8)
                    );

            X509Certificate cert =
                    (X509Certificate) factory.generateCertificate(stream);

            publicKey = cert.getPublicKey();

            logger.info("Fingpay certificate loaded successfully");

        } catch (Exception e) {

            logger.error("Failed to load Fingpay certificate", e);

            throw new RuntimeException(
                    "Failed to load Fingpay public certificate",
                    e
            );
        }
    }

    /* -------------------------------
        RSA ENCRYPT SESSION KEY
    -------------------------------- */

    public String encrypt(String sessionKey) throws Exception {

        Cipher cipher =
                Cipher.getInstance(RSA_TRANSFORMATION);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encrypted =
                cipher.doFinal(
                        Base64.getDecoder().decode(sessionKey)
                );

        return Base64.getEncoder()
                .encodeToString(encrypted);
    }
}