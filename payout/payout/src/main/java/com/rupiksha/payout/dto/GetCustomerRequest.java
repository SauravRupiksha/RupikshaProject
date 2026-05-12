package com.rupiksha.payout.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetCustomerRequest {

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @NotBlank(message = "PartnerSubId is required")
    private String partnerSubId;

}