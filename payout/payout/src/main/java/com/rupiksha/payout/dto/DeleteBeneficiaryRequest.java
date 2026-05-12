package com.rupiksha.payout.dto;

import lombok.Data;

@Data
public class DeleteBeneficiaryRequest {

    private String mobileNumber;
    private String beneficiaryId;

}