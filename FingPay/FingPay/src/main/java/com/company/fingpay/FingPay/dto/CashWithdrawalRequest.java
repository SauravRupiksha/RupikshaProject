package com.company.fingpay.FingPay.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @DecimalMin(value = "1.0", message = "Transaction amount must be greater than 0")
    private BigDecimal transactionAmount;

    @NotBlank(message = "Payment type is required")
    private String paymentType;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number")
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