package com.rupiksha.payout.dto;

import lombok.Data;

@Data
public class AddCustomerRequest {

    private String fName;
    private String lName;
    private String mobileNumber;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String pin;
    private String partnerSubId;

}