package com.rupiksha.fingpayaeps.faeps.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonPropertyOrder({ "merchantLoginId", "superMerchantId" }) // ⚠️ Important for hash
public class EkycStatusRequest {

    @NotBlank(message = "Merchant Login ID is required")
    private String merchantLoginId;

    @NotNull(message = "Super Merchant ID is required")
    private Integer superMerchantId;
}