package com.company.fingpay.FingPay.dto;





import lombok.Data;

@Data
public class BalanceEnquiryRequest {

    private CardNumberOrUID cardnumberORUID;

    private String mobileNumber;

    private String paymentType;

    private String timestamp;

    private String transactionType;

    private double latitude;

    private double longitude;

    private String requestRemarks;

    private String deviceTransactionId;

    private CaptureResponse captureResponse;

    private String languageCode;

    private String merchantTransactionId;

    private String merchantUserName;

    private String merchantPin;

}

