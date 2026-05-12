package com.rupiksha.payout.dto;

import lombok.Data;

@Data
public class AccountVerificationRequest {

    private String mobileNumber;
    private String channel;
    private String clientUniqueId;
    private String beneIFSCCode;
    private String beneAccountNo;
    private String beneName;
    private String customerName;
    private String customerMobileNo;
    private String bankName;

}