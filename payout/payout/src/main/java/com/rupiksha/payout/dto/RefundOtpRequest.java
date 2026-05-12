package com.rupiksha.payout.dto;

import lombok.Data;

@Data
public class RefundOtpRequest {

    private String mobileNumber;
    private String txnId;
    private String amount;

}