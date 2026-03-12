package com.company.fingpay.FingPay.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CardNumberOrUID {

    @NotBlank(message = "Aadhaar number is required")
    private String aadhaarNumber;

    @NotNull(message = "Indicator for UID is required")
    private Integer indicatorForUID;

    private String nationalBankIdentificationNumber;

    private String virtualId;

}
