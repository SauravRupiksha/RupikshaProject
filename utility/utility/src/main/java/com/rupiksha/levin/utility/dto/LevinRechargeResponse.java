package com.rupiksha.levin.utility.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LevinRechargeResponse {

    @JsonProperty("txnid")
    private String txnId;

    @JsonProperty("operator_ref")
    private String operatorRef;

    @JsonProperty("status_id")
    private String statusId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("client_id")
    private String clientId;
}