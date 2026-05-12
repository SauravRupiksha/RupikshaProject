package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountVerificationRequest {

    @JsonProperty("api_token")
    private String apiToken;

    @JsonProperty("mobile_number")
    private String mobileNumber;

    @JsonProperty("user_id")
    private String userId;

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

    @JsonProperty("CustomerMobileNo")
    private String customerMobileNo;

    @JsonProperty("bank_name")
    private String bankName;
}