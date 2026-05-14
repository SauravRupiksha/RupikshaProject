package com.rupiksha.levin.utility.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BbpsFetchBillResponse {

    private String txnid;

    @JsonProperty("operator_ref")
    private String operatorRef;

    @JsonProperty("status_id")
    private String statusId;

    private String status;

    private String message;

    private String reason;

    @JsonProperty("customername")
    private String customerName;

    @JsonProperty("DueDate")
    private String dueDate;

    @JsonProperty("bill_context")
    private String billContext;

    @JsonProperty("bill_amount")
    private String amount;
}