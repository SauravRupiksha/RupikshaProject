package com.rupiksha.payout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountVerificationResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    private String message;

    @JsonProperty("TxnID")
    private String txnId;

    @JsonProperty("TxnDescription")
    private String txnDescription;

    @JsonProperty("AmountRequested")
    private String amountRequested;

    @JsonProperty("BeneName")
    private String beneName;

    private String utr;

}