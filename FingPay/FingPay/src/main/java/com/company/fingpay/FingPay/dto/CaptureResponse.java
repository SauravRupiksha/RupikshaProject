package com.company.fingpay.FingPay.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CaptureResponse {

    private String errCode;
    private String errInfo;

    private String fCount;
    private String fType;

    private String iCount;
    private String iType;

    private String pCount;
    private String pType;

    private String qScore;

    private String dpId;
    private String rdsId;
    private String rdsVer;

    private String dc;
    private String mi;
    private String mc;

    private String ci;

    @NotBlank(message = "Session key is required")
    private String sessionKey;

    @NotBlank(message = "HMAC is required")
    private String hmac;

    @NotBlank(message = "PID datatype is required")
    private String pidDatatype;

    @NotBlank(message = "PID data is required")
    private String pidData;
}