package com.company.fingpay.FingPay.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CardNumberOrUID {

    @NotBlank(message = "Aadhaar number is required")
    @Pattern(regexp = "^[0-9]{12}$", message = "Aadhaar number must be 12 digits")
    private String aadhaarNumber;

    @NotNull(message = "Indicator for UID is required")
    @Min(value = 0, message = "Indicator must be 0, 1 or 2")
    @Max(value = 2, message = "Indicator must be 0, 1 or 2")
    private Integer indicatorForUID;

    private String nationalBankIdentificationNumber;

    private String virtualId;

}