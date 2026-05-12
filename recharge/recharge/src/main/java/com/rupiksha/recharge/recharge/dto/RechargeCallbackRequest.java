package com.rupiksha.recharge.recharge.dto;

import lombok.Data;

@Data
public class RechargeCallbackRequest {

    private String ResponseStatus;

    private String OperatorTxnID;

    private String OrderNo;

    private String MerTxnID;

    private String AccountNo;
}