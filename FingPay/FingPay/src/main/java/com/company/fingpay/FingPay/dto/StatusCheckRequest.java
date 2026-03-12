package com.company.fingpay.FingPay.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StatusCheckRequest {

    @NotBlank(message = "Merchant login ID is required")
    private String merchantLoginId;

    @NotBlank(message = "Merchant transaction ID is required")
    private String merchantTranId;

    @NotBlank(message = "Hash is required")
    private String hash;

}
