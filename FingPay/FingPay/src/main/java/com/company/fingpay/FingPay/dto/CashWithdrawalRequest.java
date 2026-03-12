package com.company.fingpay.FingPay.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashWithdrawalRequest {

    @NotBlank(message = "Merchant transaction ID is required")
    private String merchantTranId;

    @NotBlank(message = "Merchant username is required")
    private String merchantUserName;

    @NotBlank(message = "Merchant PIN is required")
    private String merchantPin;

    @NotBlank(message = "Transaction type is required")
    private String transactionType;

    @NotNull(message = "Transaction amount is required")
    private BigDecimal transactionAmount;

    @NotBlank(message = "Payment type is required")
    private String paymentType;

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    @NotBlank(message = "Timestamp is required")
    private String timestamp;

    private String requestRemarks;

    @NotBlank(message = "Device transaction ID is required")
    private String deviceTransactionId;

    private String languageCode;

    @NotNull(message = "Card details are required")
    private CardNumberOrUID cardNumberOrUID;

    @NotNull(message = "Capture response is required")
    private CaptureResponse captureResponse;
}