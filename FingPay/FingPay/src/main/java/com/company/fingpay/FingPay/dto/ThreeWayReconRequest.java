package com.company.fingpay.FingPay.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ThreeWayReconRequest {

    @NotBlank(message = "Merchant login ID is required")
    private String merchantLoginId;

    @NotBlank(message = "From date is required")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "From date must be in yyyy-MM-dd format"
    )
    private String fromDate;

    @NotBlank(message = "To date is required")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "To date must be in yyyy-MM-dd format"
    )
    private String toDate;

    @NotNull(message = "Page number is required")
    @Min(value = 1, message = "Page number must be at least 1")
    private Integer pageNumber;

    @NotBlank(message = "Hash is required")
    private String hash;
}