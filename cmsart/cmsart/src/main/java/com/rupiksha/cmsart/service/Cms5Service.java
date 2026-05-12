package com.rupiksha.cmsart.service;

import com.rupiksha.cmsart.config.LevinConfig;
import com.rupiksha.cmsart.dto.*;
import com.rupiksha.cmsart.entity.Cms5CallbackTransaction;
import com.rupiksha.cmsart.repository.Cms5CallbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class Cms5Service {

    private final RestTemplate restTemplate;
    private final LevinConfig levinConfig;
    private final Cms5CallbackRepository callbackRepository;

    public Cms5TransactionResponse transaction(
            Cms5TransactionRequest request) {

        try {

            String url =
                    levinConfig.getBaseUrl()
                            + "/api/levin/c5/transaction";

            log.info("CMS5 Request URL : {}", url);

            Map<String, Object> body = new HashMap<>();

            body.put("api_token", levinConfig.getApiToken());
            body.put("mobile_number", request.getMobileNumber());
            body.put("aeps_agent_id", request.getAepsAgentId());
            body.put("user_id", levinConfig.getUserId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Cms5TransactionResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            Cms5TransactionResponse.class
                    );

            log.info("CMS5 Response : {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("CMS5 Transaction Failed", e);

            Cms5TransactionResponse error =
                    new Cms5TransactionResponse();

            error.setStatusId(500);
            error.setMessage("CMS5 API Failed");

            return error;
        }
    }

    //***************************************
    public Cms5CallbackResponse handleCallback(
            Cms5CallbackRequest request){

        log.info("CMS5 Callback Received : {}", request);

        Cms5CallbackResponse response =
                new Cms5CallbackResponse();

        response.setClient_ref_id(request.getClient_ref_id());

        try {

            // Duplicate Check
            Optional<Cms5CallbackTransaction> existing =
                    callbackRepository.findByTxnid(
                            request.getTxnid());

            if(existing.isPresent()){

                log.info("Duplicate Callback Received : {}",
                        request.getTxnid());

                response.setStatus_id("1");
                response.setMessage("success");

                return response;
            }

            // Save Transaction
            Cms5CallbackTransaction txn =
                    new Cms5CallbackTransaction();

            txn.setStatusId(request.getStatus_id());
            txn.setTxnid(request.getTxnid());
            txn.setAmount(request.getAmount());
            txn.setMessage(request.getMessage());
            txn.setUserId(request.getUser_id());
            txn.setOperatorRefId(request.getOperator_ref_id());
            txn.setType(request.getType());
            txn.setApiAgentId(request.getApi_agent_id());
            txn.setTimestamp(request.getTimestamp());
            txn.setClientRefId(request.getClient_ref_id());
            txn.setProviderName(request.getProvider_name());
            txn.setProviderId(request.getProvider_id());
            txn.setCaNo(request.getCa_no());
            txn.setDescription(request.getDescription());

            callbackRepository.save(txn);

            // Step 1 : status_id 9 (Initiated)
            if("9".equals(request.getStatus_id())){

                log.info("Transaction Initiated");

                response.setStatus_id("1");
                response.setMessage("success");

                return response;
            }

            // Step 2 : status_id 1 (Success)
            if("1".equals(request.getStatus_id())){

                log.info("Transaction Success");

                response.setStatus_id("1");
                response.setMessage("success");

                return response;
            }

            // Failed
            response.setStatus_id("2");
            response.setMessage("failed");

            return response;

        } catch (Exception e){

            log.error("Callback Save Failed", e);

            response.setStatus_id("2");
            response.setMessage("failed");

            return response;
        }
    }

    //***************************
    public Cms5StatusResponse transactionStatus(
            Cms5StatusRequest request){

        try {

            String url =
                    levinConfig.getBaseUrl()
                            + "/api/levin/transaction-status";

            Map<String, Object> body = new HashMap<>();

            body.put("api_token", levinConfig.getApiToken());
            body.put("mobile_number", request.getMobileNumber());
            body.put("user_id", levinConfig.getUserId());
            body.put("txnid", request.getTxnid());
            body.put("client_ref_id", request.getClientRefId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String,Object>> entity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Cms5StatusResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            Cms5StatusResponse.class
                    );

            return response.getBody();

        } catch (Exception e){

            log.error("Transaction Status Failed", e);

            Cms5StatusResponse error =
                    new Cms5StatusResponse();

            error.setStatusId(500);
            error.setMessage("Status API Failed");

            return error;
        }
    }
}