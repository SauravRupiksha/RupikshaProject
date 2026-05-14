package com.rupiksha.recharge.recharge.dto;

import lombok.Data;

@Data
public class RechargeResponse {

    private String responseStatus;
    private String description;
    private String merchantRefNo;
    private String mobileNo;
    private String amount;
    private String operatorTxnId;
    private String orderNo;
}