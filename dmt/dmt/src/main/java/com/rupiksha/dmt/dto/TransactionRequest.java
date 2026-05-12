package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionRequest {

    @JsonProperty("api_token")
    private String apiToken;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("ClientUniqueID")
    private String clientUniqueID;

    @JsonProperty("BeneIFSCCode")
    private String beneIFSCCode;

    @JsonProperty("BeneAccountNo")
    private String beneAccountNo;

    @JsonProperty("BeneName")
    private String beneName;

    @JsonProperty("CustomerName")
    private String customerName;

    @JsonProperty("Amount")
    private String amount;

    @JsonProperty("pan_number")
    private String panNumber;

    @JsonProperty("pin_code")
    private String pinCode;

    @JsonProperty("CustomerMobileNo")
    private String customerMobileNo;

    @JsonProperty("BankName")
    private String bankName;

    @JsonProperty("token")
    private String token;
}
