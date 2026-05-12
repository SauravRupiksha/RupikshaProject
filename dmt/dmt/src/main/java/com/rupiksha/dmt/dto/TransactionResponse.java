package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    private String message;

    @JsonProperty("TxnID")
    private String txnId;   // changed to String

    @JsonProperty("TxnDescription")
    private String txnDescription;

    @JsonProperty("AmountRequested")
    private String amountRequested;   // changed to String

    @JsonProperty("BeneName")
    private String beneName;

    @JsonProperty("client_req_no")
    private String clientReqNo;   // added

    private String utr;
}
