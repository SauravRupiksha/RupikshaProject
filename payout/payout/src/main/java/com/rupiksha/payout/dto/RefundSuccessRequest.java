package com.rupiksha.payout.dto;

import lombok.Data;

@Data
public class RefundSuccessRequest {

    private String mobileNumber;
    private String txnId;
    private String amount;
    private String otp;

}