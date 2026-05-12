package com.rupiksha.payout.service;


import com.rupiksha.payout.config.LevinConfig;
import com.rupiksha.payout.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LevinDmtService {

    private final LevinConfig levinConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GetCustomerResponse getCustomer(GetCustomerRequest request) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/cashout/api/levin/payoutcash-get_customer";

            Map<String, Object> payload = new HashMap<>();
            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());
            payload.put("mobile_number", request.getMobileNumber());
            payload.put("partnerSubId", request.getPartnerSubId());

            String json = objectMapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<GetCustomerResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            GetCustomerResponse.class
                    );

            log.info("Levin Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Levin API Error", e);

            GetCustomerResponse error = new GetCustomerResponse();
            error.setStatusId(500);
            error.setMessage(e.getMessage());

            return error;
        }
    }
    //*************************************
    public AddCustomerResponse addCustomer(AddCustomerRequest request) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/super/api/levin/add-customer";

            Map<String, Object> payload = new HashMap<>();
            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());
            payload.put("f_name", request.getFName());
            payload.put("l_name", request.getLName());
            payload.put("mobile_number", request.getMobileNumber());
            payload.put("address1", request.getAddress1());
            payload.put("address2", request.getAddress2());
            payload.put("city", request.getCity());
            payload.put("state", request.getState());
            payload.put("country", request.getCountry());
            payload.put("pin", request.getPin());
            payload.put("partnerSubId", request.getPartnerSubId());

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<AddCustomerResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            AddCustomerResponse.class
                    );

            log.info("Add Customer Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Add Customer Error", e);

            AddCustomerResponse error = new AddCustomerResponse();
            error.setStatusId(500);
            error.setMessage(e.getMessage());

            return error;
        }
    }
    //***************************
    public AddCustomerConfirmResponse addCustomerConfirm(
            AddCustomerConfirmRequest request) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/super/api/levin/add-customer-confirm";

            Map<String, Object> payload = new HashMap<>();
            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());
            payload.put("mobile_number", request.getMobileNumber());
            payload.put("otp", request.getOtp());
            payload.put("state", request.getState());
            payload.put("partnerSubId", request.getPartnerSubId());

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<AddCustomerConfirmResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            AddCustomerConfirmResponse.class
                    );

            log.info("Add Customer Confirm Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Add Customer Confirm Error", e);

            AddCustomerConfirmResponse error =
                    new AddCustomerConfirmResponse();

            error.setStatusId(500);
            error.setMessage(e.getMessage());

            return error;
        }
    }

    //*********************************************
    public AddBeneficiaryResponse addBeneficiary(
            AddBeneficiaryRequest request) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/super/api/levin/add-beneficiary";

            Map<String, Object> payload = new HashMap<>();
            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());
            payload.put("mobile_number", request.getMobileNumber());
            payload.put("bene_name", request.getBeneName());
            payload.put("number", request.getNumber());
            payload.put("bank_account", request.getBankAccount());
            payload.put("bank_name", request.getBankName());
            payload.put("ifsc", request.getIfsc());
            payload.put("partnerSubId", request.getPartnerSubId());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<AddBeneficiaryResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            AddBeneficiaryResponse.class
                    );

            log.info("Add Beneficiary Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Add Beneficiary Error", e);

            AddBeneficiaryResponse error =
                    new AddBeneficiaryResponse();

            error.setStatusId(500);
            error.setMessage(e.getMessage());

            return error;
        }
    }
    //***********************************
    public GetAllBeneficiaryResponse getAllBeneficiary(String mobileNumber) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/super/api/levin/get-all-beneficiary";

            Map<String, Object> payload = new HashMap<>();
            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());
            payload.put("mobile_number", mobileNumber);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<GetAllBeneficiaryResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            GetAllBeneficiaryResponse.class
                    );

            log.info("Get All Beneficiary Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Get Beneficiary Error", e);

            GetAllBeneficiaryResponse error =
                    new GetAllBeneficiaryResponse();

            error.setStatusId(500);

            return error;
        }
    }
    //******************************************
    public TransactionResponse transaction(
            TransactionRequest request) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/cashout/api/levin/payout-transaction";

            Map<String, Object> payload = new HashMap<>();

            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());
            payload.put("channel", request.getChannel());
            payload.put("ClientUniqueID", request.getClientUniqueId());
            payload.put("BeneIFSCCode", request.getBeneIFSCCode());
            payload.put("BeneAccountNo", request.getBeneAccountNo());
            payload.put("BeneName", request.getBeneName());
            payload.put("CustomerName", request.getCustomerName());
            payload.put("Amount", request.getAmount());
            payload.put("bene_id", request.getBeneId());
            payload.put("pan_number", request.getPanNumber());
            payload.put("pin_code", request.getPinCode());
            payload.put("CustomerMobileNo", request.getCustomerMobileNo());
            payload.put("partnerSubId", request.getPartnerSubId());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<TransactionResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            TransactionResponse.class
                    );

            log.info("Transaction Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Transaction Error", e);

            TransactionResponse error = new TransactionResponse();
            error.setStatusId(500);
            error.setMessage(e.getMessage());

            return error;
        }
    }
    //***************************************************
    public TransactionStatusResponse transactionStatus(
            TransactionStatusRequest request) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/super/api/levin/transaction-status";

            Map<String, Object> payload = new HashMap<>();

            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());
            payload.put("mobile_number", request.getMobileNumber());
            payload.put("txnid", request.getTxnId());
            payload.put("client_ref_id", request.getClientRefId());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<TransactionStatusResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            TransactionStatusResponse.class
                    );

            log.info("Transaction Status Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Transaction Status Error", e);

            TransactionStatusResponse error =
                    new TransactionStatusResponse();

            error.setStatusId(500);
            error.setMessage(e.getMessage());

            return error;
        }
    }
    //*************************************
    public GenerateOtpResponse generateOtp(
            GenerateOtpRequest request) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/super/api/levin/generate-otp";

            Map<String, Object> payload = new HashMap<>();

            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());
            payload.put("mobile_number", request.getMobileNumber());
            payload.put("partnerSubId", request.getPartnerSubId());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<GenerateOtpResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            GenerateOtpResponse.class
                    );

            log.info("Generate OTP Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Generate OTP Error", e);

            GenerateOtpResponse error =
                    new GenerateOtpResponse();

            error.setStatusId(500);
            error.setMessage(e.getMessage());

            return error;
        }
    }
    //********************************
    public AccountVerificationResponse accountVerification(
            AccountVerificationRequest request) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/super/api/levin/account-name-info";

            Map<String, Object> payload = new HashMap<>();

            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());
            payload.put("mobile_number", request.getMobileNumber());
            payload.put("channel", request.getChannel());
            payload.put("ClientUniqueID", request.getClientUniqueId());
            payload.put("BeneIFSCCode", request.getBeneIFSCCode());
            payload.put("BeneAccountNo", request.getBeneAccountNo());
            payload.put("BeneName", request.getBeneName());
            payload.put("CustomerName", request.getCustomerName());
            payload.put("CustomerMobileNo", request.getCustomerMobileNo());
            payload.put("bank_name", request.getBankName());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<AccountVerificationResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            AccountVerificationResponse.class
                    );

            log.info("Account Verification Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Account Verification Error", e);

            AccountVerificationResponse error =
                    new AccountVerificationResponse();

            error.setStatusId(500);
            error.setMessage(e.getMessage());

            return error;
        }
    }
    //*******************************************
    public DeleteBeneficiaryResponse deleteBeneficiary(
            DeleteBeneficiaryRequest request) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/super/api/levin/delete-beneficiary";

            Map<String, Object> payload = new HashMap<>();

            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());   // ✅ Missing field
            payload.put("mobile_number", request.getMobileNumber());
            payload.put("beneficiary_id", request.getBeneficiaryId());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<DeleteBeneficiaryResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            DeleteBeneficiaryResponse.class
                    );

            log.info("Delete Beneficiary Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Delete Beneficiary Error", e);

            DeleteBeneficiaryResponse error =
                    new DeleteBeneficiaryResponse();

            error.setStatusId(500);
            error.setMessage(e.getMessage());

            return error;
        }
    }
    //***************************************
    public RefundOtpResponse refundOtp(
            RefundOtpRequest request) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/super/api/levin/refund-otp";

            Map<String, Object> payload = new HashMap<>();

            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());
            payload.put("mobile_number", request.getMobileNumber());
            payload.put("txnid", request.getTxnId());
            payload.put("amount", request.getAmount());

            String json = objectMapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<RefundOtpResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            RefundOtpResponse.class
                    );

            log.info("Refund OTP Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Refund OTP Error", e);

            RefundOtpResponse error =
                    new RefundOtpResponse();

            error.setStatusId(500);
            error.setMessage(e.getMessage());

            return error;
        }
    }
    //*******************************************
    public RefundSuccessResponse refundSuccess(
            RefundSuccessRequest request) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/super/api/levin/refund-success";

            Map<String, Object> payload = new HashMap<>();

            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());
            payload.put("mobile_number", request.getMobileNumber());
            payload.put("txnid", request.getTxnId());
            payload.put("amount", request.getAmount());
            payload.put("otp", request.getOtp());

            String json = objectMapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<RefundSuccessResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            RefundSuccessResponse.class
                    );

            log.info("Refund Success Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Refund Success Error", e);

            RefundSuccessResponse error =
                    new RefundSuccessResponse();

            error.setStatusId(500);
            error.setMessage(e.getMessage());

            return error;
        }
    }
    //************************************
    public GetBalanceResponse getBalance(
            GetBalanceRequest request) {

        try {

            String url = levinConfig.getBaseUrl()
                    + "/super/api/levin/get-balance";

            Map<String, Object> payload = new HashMap<>();

            payload.put("api_token", levinConfig.getApiToken());
            payload.put("user_id", levinConfig.getUserId());
            payload.put("mobile_number", request.getMobileNumber());

            String json = objectMapper.writeValueAsString(payload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            ResponseEntity<GetBalanceResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            GetBalanceResponse.class
                    );

            log.info("Get Balance Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {

            log.error("Get Balance Error", e);

            GetBalanceResponse error =
                    new GetBalanceResponse();

            error.setStatus(500);
            error.setMessage(e.getMessage());

            return error;
        }
    }
    //**********************************

}