package com.rupiksha.dmt.dto;

import lombok.Data;

@Data
public class AddBeneficiaryBody {

    private String mobile;
    private String beneName;
    private String number;
    private String bankAccount;
    private String bankName;
    private String ifsc;
    private String partnerSubId;

}