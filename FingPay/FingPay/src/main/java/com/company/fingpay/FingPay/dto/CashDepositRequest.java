package com.company.fingpay.FingPay.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashDepositRequest {

    @NotNull(message = "Card details are required")
    private CardNumberOrUID cardNumberOrUID;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number")
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

    private Boolean facialTxn;

    private Boolean irisTxn;

    @NotBlank(message = "Device transaction ID is required")
    private String deviceTransactionId;

    @NotNull(message = "Capture response is required")
    private CaptureResponse captureResponse;

    private String languageCode;

    @NotNull(message = "Transaction amount is required")
    @DecimalMin(value = "1.0", message = "Transaction amount must be greater than 0")
    private BigDecimal transactionAmount;

    @NotBlank(message = "Merchant transaction ID is required")
    private String merchantTranId;

    @NotBlank(message = "Merchant username is required")
    private String merchantUserName;

    @NotBlank(message = "Merchant PIN is required")
    private String merchantPin;
}