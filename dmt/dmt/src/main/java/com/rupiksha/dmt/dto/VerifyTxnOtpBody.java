package com.rupiksha.dmt.dto;

import lombok.Data;

@Data
public class VerifyTxnOtpBody {

    private String mobile;
    private String otp;
    private String token;
}