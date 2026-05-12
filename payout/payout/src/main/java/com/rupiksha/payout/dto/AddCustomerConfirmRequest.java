package com.rupiksha.payout.dto;

import lombok.Data;

@Data
public class AddCustomerConfirmRequest {

    private String mobileNumber;
    private String otp;
    private String state;
    private String partnerSubId;

}