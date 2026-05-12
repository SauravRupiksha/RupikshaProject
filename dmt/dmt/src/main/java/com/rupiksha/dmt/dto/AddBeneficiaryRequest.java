package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddBeneficiaryRequest {

    @JsonProperty("api_token")
    private String apiToken;

    @JsonProperty("mobile_number")
    private String mobileNumber;

    @JsonProperty("bene_name")
    private String beneName;

    private String number;

    @JsonProperty("bank_account")
    private String bankAccount;

    @JsonProperty("bank_name")
    private String bankName;

    private String ifsc;

    @JsonProperty("user_id")
    private String userId;

    private String partnerSubId;
}