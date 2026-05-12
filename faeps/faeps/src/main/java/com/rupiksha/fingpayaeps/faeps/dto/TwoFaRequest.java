package com.rupiksha.fingpayaeps.faeps.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
        "superMerchantId",
        "merchantUserName",
        "merchantPin",
        "transactionType",
        "serviceType",
        "merchantTranId",
        "requestRemarks",
        "latitude",
        "longitude",
        "mobileNumber",
        "cardnumberORUID",
        "captureResponse"
})
public class TwoFaRequest {

    @JsonProperty("superMerchantId")
    private int superMerchantId;

    @JsonProperty("merchantUserName")
    @NotBlank
    private String merchantUserName;

    @JsonProperty("merchantPin")
    @NotBlank
    private String merchantPin;

    @JsonProperty("transactionType")
    private String transactionType;

    @JsonProperty("serviceType")
    private String serviceType;

    @JsonProperty("merchantTranId")
    @NotBlank
    private String merchantTranId;

    @JsonProperty("requestRemarks")
    private String requestRemarks;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("mobileNumber")
    @NotBlank
    @Pattern(regexp = "\\d{10}")
    private String mobileNumber;

    @JsonProperty("cardnumberORUID")
    @Valid
    private TwoFaCardnumberORUID cardnumberORUID;

    @JsonProperty("captureResponse")
    @Valid
    private TwoFaCaptureResponse captureResponse;
}