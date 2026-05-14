package com.rupiksha.levin.utility.dto;

import lombok.Data;

@Data
public class RechargeRequest {

    private String number;

    private String providerId;

    private Double amount;

    // Optional
    private String providerCode = "NA";

    // Optional BBPS fields
    private String bbpsDueDate;

    private String bbpsBillUnits;

    private String mobileNumber;

    // Optional for Jio recharge
    private String jioPlanId;

    // Loan Repayment Extra Field
    private String field12;
}