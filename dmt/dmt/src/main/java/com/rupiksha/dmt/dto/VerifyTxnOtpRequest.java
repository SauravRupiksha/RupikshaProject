package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VerifyTxnOtpRequest {

    @JsonProperty("api_token")
    private String apiToken;

    @JsonProperty("user_id")
    private String userId;

    private String otp;
    private String token;

    @JsonProperty("mobile_number")
    private String mobileNumber;
}