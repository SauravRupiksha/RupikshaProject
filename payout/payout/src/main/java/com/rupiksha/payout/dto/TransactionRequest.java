package com.rupiksha.payout.dto;

import lombok.Data;

@Data
public class TransactionRequest {

    private String channel;
    private String clientUniqueId;
    private String beneIFSCCode;
    private String beneAccountNo;
    private String beneName;
    private String customerName;
    private String amount;
    private String beneId;
    private String panNumber;
    private String pinCode;
    private String customerMobileNo;
    private String partnerSubId;

}