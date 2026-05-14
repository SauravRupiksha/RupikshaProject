package com.rupiksha.recharge.recharge.dto;

import lombok.Data;

@Data
public class RechargeRequest {

    private String mobileNo;
    private String operatorCode;
    private String merchantRefNo;
    private String serviceType;
    private String amount;
}