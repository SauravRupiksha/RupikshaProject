package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BankDownCallbackRequest {

    @JsonProperty("bank_down_code")
    private String bankDownCode;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("bank_status")
    private String bankStatus;

    @JsonProperty("user_id")
    private String userId;

    private String type;
}