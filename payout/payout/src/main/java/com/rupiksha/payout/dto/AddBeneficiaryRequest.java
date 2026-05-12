package com.rupiksha.payout.dto;

import lombok.Data;

@Data
public class AddBeneficiaryRequest {

    private String mobileNumber;
    private String beneName;
    private String number;
    private String bankAccount;
    private String bankName;
    private String ifsc;
    private String partnerSubId;

}