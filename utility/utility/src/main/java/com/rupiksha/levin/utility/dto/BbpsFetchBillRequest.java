package com.rupiksha.levin.utility.dto;

import lombok.Data;

@Data
public class BbpsFetchBillRequest {

    private String number;

    private String providerId;

    private String bbpsBillUnits;

    private String retailerMobileNumber;

    private String districtDiscome;
}