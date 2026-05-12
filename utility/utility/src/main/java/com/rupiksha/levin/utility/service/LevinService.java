package com.rupiksha.levin.utility.service;

import com.rupiksha.levin.utility.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class LevinService {

    private final RestTemplate restTemplate;

    @Value("${levin.base-url}")
    private String baseUrl;

    @Value("${levin.user-id}")
    private String userId;

    @Value("${levin.api-token}")
    private String apiToken;

    // =========================================================
    // RECHARGE API
    // =========================================================

    public LevinRechargeResponse doRecharge(
            RechargeRequest request
    ) {

        String url =
                baseUrl +
                        "/api/levin/recharge/recharge-now";

        String clientId = generateClientId();

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();

        body.add("api_token", apiToken);
        body.add("number", request.getNumber());
        body.add("provider_id", request.getProviderId());

        body.add(
                "amount",
                String.valueOf(request.getAmount())
        );

        body.add("mode", "Web");
        body.add("user_id", userId);
        body.add("client_id", clientId);

        body.add(
                "provider_code",
                request.getProviderCode() != null
                        ? request.getProviderCode()
                        : "NA"
        );

        // Optional Fields
        body.add(
                "field12",
                request.getField12() != null
                        ? request.getField12()
                        : "NA"
        );

        if (request.getBbpsDueDate() != null) {

            body.add(
                    "bbps_due_date",
                    request.getBbpsDueDate()
            );
        }

        if (request.getBbpsBillUnits() != null) {

            body.add(
                    "bbps_bill_units",
                    request.getBbpsBillUnits()
            );
        }

        if (request.getMobileNumber() != null) {

            body.add(
                    "mobile_number",
                    request.getMobileNumber()
            );
        }

        if (request.getJioPlanId() != null) {

            body.add(
                    "jio_plan_id",
                    request.getJioPlanId()
            );
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_FORM_URLENCODED
        );

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(body, headers);

        try {

            MultiValueMap<String, String> logBody =
                    new LinkedMultiValueMap<>(body);

            logBody.remove("api_token");

            log.info(
                    "LEVIN REQUEST : {}",
                    logBody
            );

            ResponseEntity<LevinRechargeResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            LevinRechargeResponse.class
                    );

            log.info(
                    "LEVIN RESPONSE : {}",
                    response.getBody()
            );

            return response.getBody();

        } catch (Exception e) {

            log.error("LEVIN ERROR : ", e);

            LevinRechargeResponse errorResponse =
                    new LevinRechargeResponse();

            errorResponse.setStatusId("0");
            errorResponse.setMessage(e.getMessage());
            errorResponse.setClientId(clientId);

            return errorResponse;
        }
    }

    // =========================================================
    // BBPS FETCH BILL API
    // =========================================================

    public BbpsFetchBillResponse fetchBill(
            BbpsFetchBillRequest request
    ) {

        String url =
                baseUrl +
                        "/api/levin/bbps/get-biller-details";

        String clientId = generateClientId();

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();

        body.add("api_token", apiToken);
        body.add("number", request.getNumber());
        body.add("provider_id", request.getProviderId());
        body.add("user_id", userId);
        body.add("client_id", clientId);

        // Mandatory Field

        body.add(
                "Retailer_MobileNumber",
                request.getRetailerMobileNumber()
        );

        // Optional Fields

        if (request.getBbpsBillUnits() != null) {

            body.add(
                    "bbps_bill_units",
                    request.getBbpsBillUnits()
            );
        }

        if (request.getDistrictDiscome() != null) {

            body.add(
                    "district_discome",
                    request.getDistrictDiscome()
            );
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_FORM_URLENCODED
        );

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(body, headers);

        try {

            MultiValueMap<String, String> logBody =
                    new LinkedMultiValueMap<>(body);

            logBody.remove("api_token");

            log.info(
                    "BBPS FETCH BILL REQUEST : {}",
                    logBody
            );

            ResponseEntity<BbpsFetchBillResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            BbpsFetchBillResponse.class
                    );

            log.info(
                    "BBPS FETCH BILL RESPONSE : {}",
                    response.getBody()
            );

            return response.getBody();

        } catch (Exception e) {

            log.error(
                    "BBPS FETCH BILL ERROR : ",
                    e
            );

            BbpsFetchBillResponse errorResponse =
                    new BbpsFetchBillResponse();

            errorResponse.setStatusId("0");
            errorResponse.setMessage(e.getMessage());

            return errorResponse;
        }
    }
    // =========================================================
// BBPS PAY BILL API
// =========================================================

    public BbpsPayBillResponse payBill(
            BbpsPayBillRequest request
    ) {

        String url =
                baseUrl +
                        "/api/levin/bbps/pay-recharge-bill";

        String clientId = generateClientId();

        MultiValueMap<String, String> body =
                new LinkedMultiValueMap<>();

        body.add("api_token", apiToken);

        body.add("number", request.getNumber());

        body.add("provider_id", request.getProviderId());

        body.add(
                "amount",
                String.valueOf(request.getAmount())
        );

        body.add("user_id", userId);

        body.add("client_id", clientId);

        // Mandatory Fields

        body.add(
                "mobile_number",
                request.getMobileNumber()
        );

        body.add(
                "bill_context",
                request.getBillContext()
        );

        // Optional Fields

        if (request.getBbpsDueDate() != null) {

            body.add(
                    "bbps_due_date",
                    request.getBbpsDueDate()
            );
        }

        if (request.getBbpsBillUnits() != null) {

            body.add(
                    "bbps_bill_units",
                    request.getBbpsBillUnits()
            );
        }

        // LOAN / EMI EXTRA FIELD

        if (request.getField12() != null) {

            body.add(
                    "field12",
                    request.getField12()
            );
        }

        // EDUCATION FEES EXTRA FIELD

        if (request.getField13() != null) {

            body.add(
                    "field13",
                    request.getField13()
            );
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_FORM_URLENCODED
        );

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(body, headers);

        try {

            MultiValueMap<String, String> logBody =
                    new LinkedMultiValueMap<>(body);

            logBody.remove("api_token");

            log.info(
                    "BBPS PAY BILL REQUEST : {}",
                    logBody
            );

            ResponseEntity<BbpsPayBillResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            BbpsPayBillResponse.class
                    );

            log.info(
                    "BBPS PAY BILL RESPONSE : {}",
                    response.getBody()
            );

            return response.getBody();

        } catch (Exception e) {

            log.error(
                    "BBPS PAY BILL ERROR : ",
                    e
            );

            BbpsPayBillResponse errorResponse =
                    new BbpsPayBillResponse();

            errorResponse.setStatusId("0");

            errorResponse.setMessage(
                    e.getMessage()
            );

            errorResponse.setClientId(clientId);

            return errorResponse;
        }
    }
    // =========================================================
    // CLIENT ID GENERATOR
    // =========================================================

    private String generateClientId() {

        return "LVN" + System.currentTimeMillis();
    }
}