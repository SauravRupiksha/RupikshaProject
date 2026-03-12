package com.company.fingpay.FingPay.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ThreeWayReconRequest {

    @NotBlank(message = "Merchant login ID is required")
    private String merchantLoginId;

    @NotBlank(message = "From date is required")
    private String fromDate;

    @NotBlank(message = "To date is required")
    private String toDate;

    @NotNull(message = "Page number is required")
    private Integer pageNumber;

    @NotBlank(message = "Hash is required")
    private String hash;

}
