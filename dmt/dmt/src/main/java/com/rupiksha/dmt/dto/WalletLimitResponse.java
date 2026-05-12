package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletLimitResponse {

    @JsonProperty("status_id")
    private String statusId;

    private String message;

    @JsonProperty("f_name")
    private String firstName;

    @JsonProperty("l_name")
    private String lastName;

    private String limit;

    @JsonProperty("wallet1_status")
    private String wallet1Status;

    @JsonProperty("wallet2_status")
    private String wallet2Status;

    @JsonProperty("wallet3_status")
    private String wallet3Status;

    private String wallet1;
    private String wallet2;
    private String wallet3;

    private String currency;
}