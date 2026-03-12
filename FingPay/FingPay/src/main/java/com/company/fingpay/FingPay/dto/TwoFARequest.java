package com.company.fingpay.FingPay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TwoFARequest {

    @NotNull(message = "Super merchant ID is required")
    private Integer superMerchantId;

    @NotBlank(message = "Merchant username is required")
    private String merchantUserName;

    @NotBlank(message = "Merchant PIN is required")
    @Size(min = 4, max = 6, message = "Merchant PIN must be between 4 and 6 characters")
    private String merchantPin;

    @NotBlank(message = "Transaction type is required")
    private String transactionType;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    @NotBlank(message = "Merchant transaction ID is required")
    private String merchantTranId;

    @NotBlank(message = "Service type is required")
    private String serviceType;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number")
    private String mobileNumber;

    private String requestRemarks;

    @NotNull(message = "Card details are required")
    private CardNumberOrUID cardNumberOrUID;

    @NotNull(message = "Capture response is required")
    private CaptureResponse captureResponse;
}