package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionStatusRequest {

    @JsonProperty("api_token")
    private String apiToken;

    @JsonProperty("mobile_number")
    private String mobileNumber;

    @JsonProperty("user_id")
    private String userId;

    private String txnid;

    @JsonProperty("client_ref_id")
    private String clientRefId;
}