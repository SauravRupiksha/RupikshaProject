package com.rupiksha.dmt.dto;

import lombok.Data;

@Data
public class AccountVerificationBody {

    private String mobile;
    private String channel;
    private String clientUniqueID;
    private String beneIFSCCode;
    private String beneAccountNo;
    private String beneName;
    private String customerName;
    private String customerMobileNo;
    private String bankName;
}