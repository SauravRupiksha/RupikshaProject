package com.company.fingpay.FingPay.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TwoFARequest {

    @NotNull(message = "Super merchant ID is required")
    private Integer superMerchantId;

    @NotBlank(message = "Merchant username is required")
    private String merchantUserName;

    @NotBlank(message = "Merchant PIN is required")
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
    private String mobileNumber;

    private String requestRemarks;

    @NotNull(message = "Card details are required")
    private CardNumberOrUID cardNumberOrUID;

    @NotNull(message = "Capture response is required")
    private CaptureResponse captureResponse;
}
