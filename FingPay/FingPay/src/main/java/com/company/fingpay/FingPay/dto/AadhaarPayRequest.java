package com.company.fingpay.FingPay.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AadhaarPayRequest {

    @NotNull(message = "CardNumberOrUID is required")
    private CardNumberOrUID cardNumberOrUID;

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @NotBlank(message = "Payment type is required")
    private String paymentType;

    @NotBlank(message = "Timestamp is required")
    private String timestamp;

    @NotBlank(message = "Transaction type is required")
    private String transactionType;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    private String requestRemarks;

    @NotBlank(message = "Device transaction ID is required")
    private String deviceTransactionId;

    @NotNull(message = "Capture response is required")
    private CaptureResponse captureResponse;

    private String languageCode;

    @NotNull(message = "Transaction amount is required")
    private BigDecimal transactionAmount;

    @NotBlank(message = "Merchant transaction ID is required")
    private String merchantTranId;

    @NotBlank(message = "Merchant username is required")
    private String merchantUserName;

    @NotBlank(message = "Merchant PIN is required")
    private String merchantPin;
}
