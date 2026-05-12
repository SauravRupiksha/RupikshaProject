package com.rupiksha.recharge.recharge.service;

import com.rupiksha.recharge.recharge.dto.RechargeRequest;
import com.rupiksha.recharge.recharge.dto.RechargeResponse;
import com.rupiksha.recharge.recharge.entity.RechargeTransaction;
import com.rupiksha.recharge.recharge.repository.RechargeTransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class RechargeService {

    private final RestTemplate restTemplate;

    private final RechargeTransactionRepository repository;

    @Value("${venus.base.url}")
    private String baseUrl;

    @Value("${venus.auth.key}")
    private String authKey;

    @Value("${venus.auth.pass}")
    private String authPass;

    public RechargeResponse doRecharge(
            RechargeRequest request
    ) {

        try {

            String url =
                    baseUrl +
                            "/V2/api/recharge/transaction";

            // =========================
            // HEADERS
            // =========================

            HttpHeaders headers =
                    new HttpHeaders();

            headers.set(
                    "authkey",
                    authKey
            );

            headers.set(
                    "authpass",
                    authPass
            );

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );

            // =========================
            // RAW JSON BODY
            // =========================

            String jsonBody = """

            {
                "mobileNo":"%s",
                "operatorCode":"%s",
                "merchantRefNo":"%s",
                "serviceType":"%s",
                "amount":"%s"
            }

            """.formatted(

                    request.getMobileNo(),

                    request.getOperatorCode(),

                    request.getMerchantRefNo(),

                    request.getServiceType(),

                    request.getAmount()
            );

            HttpEntity<String> entity =
                    new HttpEntity<>(
                            jsonBody,
                            headers
                    );

            // =========================
            // LOGS
            // =========================

//            log.info(
//                    "Recharge URL : {}",
//                    url
//            );
//
//            log.info(
//                    "Recharge Headers : {}",
//                    headers
//            );
//
//            log.info(
//                    "Recharge Raw JSON : {}",
//                    jsonBody
//            );

            // =========================
            // API CALL
            // =========================

            ResponseEntity<RechargeResponse>
                    response =
                    restTemplate.exchange(

                            url,

                            HttpMethod.POST,

                            entity,

                            RechargeResponse.class
                    );

            RechargeResponse apiResponse =
                    response.getBody();
//
//            log.info(
//                    "Recharge Response : {}",
//                    apiResponse
//            );

            // =========================
            // SAVE TRANSACTION
            // =========================

            if(apiResponse != null){

                RechargeTransaction txn =
                        new RechargeTransaction();

                txn.setMobileNo(
                        request.getMobileNo()
                );

                txn.setAmount(
                        request.getAmount()
                );

                txn.setOperatorCode(
                        request.getOperatorCode()
                );

                txn.setServiceType(
                        request.getServiceType()
                );

                txn.setMerchantRefNo(
                        request.getMerchantRefNo()
                );

                txn.setResponseStatus(
                        apiResponse.getResponseStatus()
                );

                txn.setOperatorTxnId(
                        apiResponse.getOperatorTxnId()
                );

                txn.setOrderNo(
                        apiResponse.getOrderNo()
                );

                repository.save(txn);
            }

            return apiResponse;

        } catch (RestClientException e) {

//            log.error(
//                    "Recharge API Error : {}",
//                    e.getMessage()
//            );

            throw new RuntimeException(
                    "Recharge Failed"
            );
        }
    }
}