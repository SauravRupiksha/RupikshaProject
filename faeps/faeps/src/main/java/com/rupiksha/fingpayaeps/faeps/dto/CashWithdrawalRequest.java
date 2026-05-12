package com.rupiksha.fingpayaeps.faeps.dto;


import lombok.Data;

@Data
public class CashWithdrawalRequest {

    private String merchantTranId;

    private CardnumberORUID cardnumberORUID;

    private String mobileNumber;

    private String paymentType;

    private String timestamp;

    private String transactionType;

    private double latitude;
    private double longitude;

    private String requestRemarks;

    private String languageCode;

    private double transactionAmount;

    private String merchantUserName;

    private String merchantPin;

    private String superMerchantId;

    private String pidXml; // 🔥 MUST

    private CaptureResponse captureResponse; // 🔥 MUST
}