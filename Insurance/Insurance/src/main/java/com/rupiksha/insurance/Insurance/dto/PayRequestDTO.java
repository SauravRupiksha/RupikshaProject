package com.rupiksha.insurance.Insurance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayRequestDTO {

    @NotBlank(message = "Order ID is required")
    @Size(min = 5, max = 50, message = "Order ID must be between 5-50 characters")
    private String orderId;
}