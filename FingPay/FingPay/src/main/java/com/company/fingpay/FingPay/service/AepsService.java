package com.company.fingpay.FingPay.service;

import com.company.fingpay.FingPay.dto.*;
import com.company.fingpay.FingPay.entity.AepsTransaction;
import com.company.fingpay.FingPay.repository.AepsTransactionRepository;
import com.company.fingpay.FingPay.util.HashUtil;
import com.company.fingpay.FingPay.util.LoggerUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AepsService {

    private static final Logger logger =
            LoggerUtil.getLogger(AepsService.class);

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AepsTransactionRepository transactionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${aeps.device.imei}")
    private String deviceImei;

    @Value("${aeps.super.merchant.id}")
    private String superMerchantId;

    @Value("${fingpay.base.url}")
    private String baseUrl;

    @Value("${fingpay.cash.withdrawal}")
    private String cashWithdrawalUrl;

    @Value("${fingpay.balance.enquiry}")
    private String balanceEnquiryUrl;

    @Value("${fingpay.mini.statement}")
    private String miniStatementUrl;

    @Value("${fingpay.aadhaar.pay}")
    private String aadhaarPayUrl;

    @Value("${fingpay.cash.deposit}")
    private String cashDepositUrl;

    @Value("${fingpay.twofa}")
    private String twoFaUrl;

    @Value("${fingpay.status}")
    private String statusUrl;

    @Value("${fingpay.recon}")
    private String reconUrl;


    /* -----------------------------------
            COMMON API CALL
    ------------------------------------ */

    private String callApi(String endpoint, String json) throws Exception {

        logger.info("AEPS Request");

        Map<String,String> enc =
                encryptionService.encryptRequest(json);

        HttpHeaders headers = new HttpHeaders();

        headers.set("hash", enc.get("hash"));
        headers.set("eskey", enc.get("eskey"));
        headers.set("deviceIMEI", deviceImei);
        headers.set("superMerchantId", superMerchantId);

        headers.set("trnTimestamp",
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                        .format(new Date()));

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity =
                new HttpEntity<>(enc.get("body"), headers);

        try {

            String response =
                    restTemplate.postForObject(
                            baseUrl + endpoint,
                            entity,
                            String.class
                    );

            logger.info("AEPS Response received");

            return response;

        } catch(HttpClientErrorException e){

            logger.error("HTTP Error {}", e.getStatusCode());
            throw new RuntimeException("HTTP Error : " + e.getStatusCode());

        } catch(ResourceAccessException e){

            logger.error("Network Error {}", e.getMessage());
            throw new RuntimeException("Network Error");
        }
    }


    /* -----------------------------------
            CASH WITHDRAWAL
    ------------------------------------ */

    public String cashWithdrawal(CashWithdrawalRequest request)
            throws Exception {

        Optional<AepsTransaction> existing =
                transactionRepository
                        .findByMerchantTranId(
                                request.getMerchantTranId());

        if(existing.isPresent()){
            throw new RuntimeException(
                    "Duplicate transaction id");
        }

        String json =
                objectMapper.writeValueAsString(request);

        AepsTransaction txn =
                saveTransaction(
                        request.getMerchantTranId(),
                        request.getTransactionAmount(),
                        "CW",
                        "PENDING"
                );

        String response =
                callApi(cashWithdrawalUrl, json);

        updateTransaction(txn, response);

        return response;
    }


    /* -----------------------------------
            BALANCE ENQUIRY
    ------------------------------------ */

    public String balanceEnquiry(BalanceEnquiryRequest request)
            throws Exception {

        String json =
                objectMapper.writeValueAsString(request);

        return callApi(balanceEnquiryUrl, json);
    }


    /* -----------------------------------
            MINI STATEMENT
    ------------------------------------ */

    public String miniStatement(MiniStatementRequest request)
            throws Exception {

        String json =
                objectMapper.writeValueAsString(request);

        return callApi(miniStatementUrl, json);
    }


    /* -----------------------------------
            AADHAAR PAY
    ------------------------------------ */

    public String aadhaarPay(AadhaarPayRequest request)
            throws Exception {

        String json =
                objectMapper.writeValueAsString(request);

        return callApi(aadhaarPayUrl, json);
    }


    /* -----------------------------------
            CASH DEPOSIT
    ------------------------------------ */

    public String cashDeposit(CashDepositRequest request)
            throws Exception {

        String json =
                objectMapper.writeValueAsString(request);

        return callApi(cashDepositUrl, json);
    }


    /* -----------------------------------
            2FA AUTH
    ------------------------------------ */

    public String twoFactorAuth(TwoFARequest request)
            throws Exception {

        String json =
                objectMapper.writeValueAsString(request);

        return callApi(twoFaUrl, json);
    }


    /* -----------------------------------
            STATUS CHECK
    ------------------------------------ */

    public String statusCheck(
            String merchantLoginId,
            String merchantTranId,
            String merchantPassword)
            throws Exception {

        String md5Password =
                DigestUtils.md5Hex(merchantPassword);

        String hash =
                HashUtil.generateStatusHash(
                        merchantTranId,
                        md5Password
                );

        Map<String,String> req =
                new HashMap<>();

        req.put("merchantLoginId", merchantLoginId);
        req.put("merchantTranId", merchantTranId);
        req.put("hash", hash);

        HttpEntity<Map<String,String>> entity =
                new HttpEntity<>(req);

        return restTemplate.postForObject(
                baseUrl + statusUrl,
                entity,
                String.class
        );
    }


    /* -----------------------------------
            THREE WAY RECON
    ------------------------------------ */

    public String threeWayRecon(String merchantLoginId,
                                String fromDate,
                                String toDate) throws Exception {

        String hash =
                HashUtil.generateReconHash(
                        merchantLoginId,
                        fromDate,
                        toDate
                );

        Map<String,Object> request =
                new HashMap<>();

        request.put("merchantLoginId", merchantLoginId);
        request.put("fromDate", fromDate);
        request.put("toDate", toDate);
        request.put("pageNumber", 1);
        request.put("hash", hash);

        HttpEntity<Map<String,Object>> entity =
                new HttpEntity<>(request);

        return restTemplate.postForObject(
                baseUrl + reconUrl,
                entity,
                String.class
        );
    }


    /* -----------------------------------
            SAVE TRANSACTION
    ------------------------------------ */

    private AepsTransaction saveTransaction(
            String merchantTranId,
            BigDecimal amount,
            String type,
            String status){

        AepsTransaction txn = new AepsTransaction();

        txn.setMerchantTranId(merchantTranId);
        txn.setAmount(amount);
        txn.setTransactionType(type);
        txn.setStatus(status);
        txn.setRetryCount(0);
        txn.setCreatedAt(LocalDateTime.now());
        txn.setUpdatedAt(LocalDateTime.now());

        return transactionRepository.save(txn);
    }


    /* -----------------------------------
            UPDATE TRANSACTION
    ------------------------------------ */

    private void updateTransaction(
            AepsTransaction txn,
            String response) throws Exception {

        JsonNode node =
                objectMapper.readTree(response);

        txn.setStatus(
                node.path("status").asText("FAILED"));

        txn.setResponseCode(
                node.path("responseCode").asText());

        txn.setResponseMessage(
                node.path("message").asText());

        txn.setUpdatedAt(LocalDateTime.now());

        transactionRepository.save(txn);
    }
}