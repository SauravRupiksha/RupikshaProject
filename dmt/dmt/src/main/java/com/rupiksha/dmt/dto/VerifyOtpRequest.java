package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VerifyOtpRequest {

    @JsonProperty("api_token")
    private String apiToken;

    @JsonProperty("mobile_number")
    private String mobileNumber;

    private String otp;

    @JsonProperty("user_id")
    private String userId;

    private String token;
}