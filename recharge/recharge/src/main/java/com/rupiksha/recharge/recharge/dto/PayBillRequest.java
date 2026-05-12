package com.rupiksha.recharge.recharge.dto;

import lombok.Data;

@Data
public class PayBillRequest {

    private String opcode;

    private String serviceType;

    private String consumerId;

    private String consumerMobileNo;

    private String amount;

    private String orderId;

    private String subDiv;

    private String field1;

    private String field2;
}