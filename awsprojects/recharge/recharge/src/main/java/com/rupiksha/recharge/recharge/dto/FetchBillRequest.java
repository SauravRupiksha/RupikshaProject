package com.rupiksha.recharge.recharge.dto;

import lombok.Data;

@Data
public class FetchBillRequest {

    private String opcode;

    private String serviceType;

    private String consumerId;

    private String consumerMobileNo;

    private String subDiv;

    private String field1;

    private String field2;
}