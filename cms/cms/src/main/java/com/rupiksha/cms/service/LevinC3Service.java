package com.rupiksha.cms.service;

import com.rupiksha.cms.config.LevinConfig;
import com.rupiksha.cms.dto.C3TransactionRequest;
import com.rupiksha.cms.dto.C3TransactionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Slf4j
@Service
public class LevinC3Service {

    private final RestTemplate restTemplate;
    private final LevinConfig levinConfig;

    public LevinC3Service(RestTemplate restTemplate,
                          LevinConfig levinConfig) {
        this.restTemplate = restTemplate;
        this.levinConfig = levinConfig;
    }

    public C3TransactionResponse transaction(C3TransactionRequest request){

        try {

            String url = levinConfig.getBaseUrl()
                    + "/api/levin/c3/transaction";

            log.info("CMS C3 Transaction Request URL: {}", url);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HashMap<String,Object> body = new HashMap<>();

            body.put("api_token", levinConfig.getApiToken());
            body.put("mobile_number", request.getMobile_number());
            body.put("aeps_agent_id", request.getAeps_agent_id());
            body.put("user_id", levinConfig.getUserId());
            body.put("aeps_agent_pin", request.getAeps_agent_pin());
            body.put("latitude", request.getLatitude());
            body.put("longitude", request.getLongitude());

            log.info("CMS Request Body : {}", body);

            HttpEntity<?> entity = new HttpEntity<>(body, headers);

            ResponseEntity<C3TransactionResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            C3TransactionResponse.class
                    );

            log.info("CMS Response : {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("CMS C3 Transaction Error : ", e);

            C3TransactionResponse error = new C3TransactionResponse();
            error.setStatus_id(500);
            error.setMessage("Transaction Failed");
            error.setRedirect_url(null);

            return error;
        }

    }

}