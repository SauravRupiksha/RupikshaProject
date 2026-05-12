package com.rupiksha.dmt.service;

import com.rupiksha.dmt.config.LevinConfig;
import com.rupiksha.dmt.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LevinService {

    private final RestTemplate restTemplate;
    private final LevinConfig config;

    public CustomerValidationResponse validateCustomer(String mobile){

        String url = config.getBaseUrl() + "/api/levin/shine-get-customer";

        CustomerValidationRequest request = new CustomerValidationRequest();
        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setMobileNumber(mobile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CustomerValidationRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<CustomerValidationResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        CustomerValidationResponse.class
                );

        return response.getBody();
    }
  //  **********************************************************
    public AadhaarValidationResponse validateAadhaar(
            String mobile,
            String aadhar,
            String token){

        String url = config.getBaseUrl() + "/api/levin/shine-aadhar-validation";

        AadhaarValidationRequest request = new AadhaarValidationRequest();

        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setMobileNumber(mobile);
        request.setAadharNumber(aadhar);
        request.setToken(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AadhaarValidationRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<AadhaarValidationResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        AadhaarValidationResponse.class
                );

        return response.getBody();
    }
    //*************************************************
    public SendOtpResponse sendOtp(String mobile){

        String url = config.getBaseUrl() + "/api/levin/shine-send-otp";

        SendOtpRequest request = new SendOtpRequest();

        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setMobileNumber(mobile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SendOtpRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<SendOtpResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        SendOtpResponse.class
                );

        return response.getBody();
    }
    //****************************************
    public VerifyOtpResponse verifyOtp(
            String mobile,
            String otp,
            String token){

        String url = config.getBaseUrl() + "/api/levin/shine-verify-otp";

        VerifyOtpRequest request = new VerifyOtpRequest();

        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setMobileNumber(mobile);
        request.setOtp(otp);
        request.setToken(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<VerifyOtpRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<VerifyOtpResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        VerifyOtpResponse.class
                );

        return response.getBody();
    }
    //****************************************
    public PerformEKYCResponse performEKYC(
            String mobile,
            String token,
            String pidata){

        String url = config.getBaseUrl() + "/api/levin/shine-aadhar-ekyc";

        PerformEKYCRequest request = new PerformEKYCRequest();

        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setMobileNumber(mobile);
        request.setToken(token);
        request.setPidata(pidata);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PerformEKYCRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<PerformEKYCResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        PerformEKYCResponse.class
                );

        return response.getBody();
    }
    //*************************************
    public AddBeneficiaryResponse addBeneficiary(
            String mobile,
            String beneName,
            String number,
            String bankAccount,
            String bankName,
            String ifsc,
            String partnerSubId) {

        String url = config.getBaseUrl() + "/api/levin/add-beneficiary";

        AddBeneficiaryRequest request = new AddBeneficiaryRequest();

        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setMobileNumber(mobile);
        request.setBeneName(beneName);
        request.setNumber(number);
        request.setBankAccount(bankAccount);
        request.setBankName(bankName);
        request.setIfsc(ifsc);
        request.setPartnerSubId(partnerSubId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AddBeneficiaryRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<AddBeneficiaryResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        AddBeneficiaryResponse.class
                );

        return response.getBody();
    }
    //**************************************
    public GetAllBeneficiaryResponse getAllBeneficiary(String mobile) {

        String url = config.getBaseUrl() + "/api/levin/get-all-beneficiary";

        GetAllBeneficiaryRequest request = new GetAllBeneficiaryRequest();

        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setMobileNumber(mobile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GetAllBeneficiaryRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<GetAllBeneficiaryResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        GetAllBeneficiaryResponse.class
                );

        return response.getBody();
    }
    //*********************************
    public TransactionOtpResponse sendTxnOtp(
            String mobile,
            String amount) {

        String url = config.getBaseUrl() + "/api/levin/shine-txn-otp";

        TransactionOtpRequest request = new TransactionOtpRequest();

        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setMobileNumber(mobile);
        request.setAmount(amount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TransactionOtpRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<TransactionOtpResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        TransactionOtpResponse.class
                );

        return response.getBody();
    }
    //*************************
    public VerifyTxnOtpResponse verifyTxnOtp(
            String mobile,
            String otp,
            String token) {

        String url = config.getBaseUrl() + "/api/levin/shine-verify-txn-otp";

        VerifyTxnOtpRequest request = new VerifyTxnOtpRequest();

        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setMobileNumber(mobile);
        request.setOtp(otp);
        request.setToken(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<VerifyTxnOtpRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<VerifyTxnOtpResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        VerifyTxnOtpResponse.class
                );

        return response.getBody();
    }
    //**************************************
    public TransactionResponse transaction(TransactionBody body) {

        String url = config.getBaseUrl() + "/super/api/levin/super-two-transaction";

        TransactionRequest request = new TransactionRequest();

        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setChannel(body.getChannel());
        request.setClientUniqueID(body.getClientUniqueID());
        request.setBeneIFSCCode(body.getBeneIFSCCode());
        request.setBeneAccountNo(body.getBeneAccountNo());
        request.setBeneName(body.getBeneName());
        request.setCustomerName(body.getCustomerName());
        request.setAmount(body.getAmount());
        request.setPanNumber(body.getPanNumber());
        request.setPinCode(body.getPinCode());
        request.setCustomerMobileNo(body.getCustomerMobileNo());
        request.setBankName(body.getBankName());
        request.setToken(body.getToken());

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<TransactionRequest> entity =
                    new HttpEntity<>(request, headers);

            System.out.println("Transaction URL: " + url);
            System.out.println("Transaction Request: " + request);

            ResponseEntity<TransactionResponse> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            TransactionResponse.class
                    );

            return response.getBody();

        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("Transaction API Failed: " + e.getMessage());
        }
    }
    //**********************************
    public TransactionStatusResponse transactionStatus(
            String mobile,
            String txnid,
            String clientRefId) {

        String url = config.getBaseUrl() +
                "/super/api/levin/transaction-status";

        TransactionStatusRequest request =
                new TransactionStatusRequest();

        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setMobileNumber(mobile);
        request.setTxnid(txnid);
        request.setClientRefId(clientRefId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TransactionStatusRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<TransactionStatusResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        TransactionStatusResponse.class
                );

        return response.getBody();
    }
    //*************************
    public AccountVerificationResponse accountVerification(
            AccountVerificationBody body) {

        String url = config.getBaseUrl() + "/api/levin/account-name-info";

        AccountVerificationRequest request =
                new AccountVerificationRequest();

        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setMobileNumber(body.getMobile());
        request.setChannel(body.getChannel());
        request.setClientUniqueID(body.getClientUniqueID());
        request.setBeneIFSCCode(body.getBeneIFSCCode());
        request.setBeneAccountNo(body.getBeneAccountNo());
        request.setBeneName(body.getBeneName());
        request.setCustomerName(body.getCustomerName());
        request.setCustomerMobileNo(body.getCustomerMobileNo());
        request.setBankName(body.getBankName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AccountVerificationRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<AccountVerificationResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        AccountVerificationResponse.class
                );

        return response.getBody();
    }
    //************************************
    public WalletLimitResponse getWalletLimit(
            WalletLimitBody body) {

        String url = config.getBaseUrl() + "/api/levin/get-wallet-limit";

        WalletLimitRequest request = new WalletLimitRequest();

        request.setApiToken(config.getApiToken());
        request.setUserId(config.getUserId());
        request.setMobileNumber(body.getMobileNumber());
        request.setFirstName(body.getFirstName());
        request.setLastName(body.getLastName());
        request.setDateOfBirth(body.getDateOfBirth());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WalletLimitRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<WalletLimitResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        WalletLimitResponse.class
                );

        return response.getBody();
    }
    //***********************************
}
