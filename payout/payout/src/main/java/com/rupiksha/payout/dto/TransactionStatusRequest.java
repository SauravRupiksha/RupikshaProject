package com.rupiksha.payout.dto;

import lombok.Data;

@Data
public class TransactionStatusRequest {

    private String mobileNumber;
    private String txnId;
    private String clientRefId;

}