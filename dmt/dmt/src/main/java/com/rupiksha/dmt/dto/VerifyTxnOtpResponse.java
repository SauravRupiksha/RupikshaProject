package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VerifyTxnOtpResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    private String message;
    private String token;
    private String tokenType;
    private String expiresIn;
}