package com.rupiksha.payout.dto;

import lombok.Data;

@Data
public class GenerateOtpRequest {

    private String mobileNumber;
    private String partnerSubId;

}