package com.rupiksha.levin.utility.dto;

import lombok.Data;

@Data
public class BbpsPayBillRequest {

    private String number;

    private String providerId;

    private Double amount;

    private String bbpsDueDate;

    private String bbpsBillUnits;

    private String mobileNumber;

    private String billContext;

    // LOAN / EDUCATION EXTRA FIELD
    private String field12;

    // EDUCATION FEES EXTRA FIELD
    private String field13;
}