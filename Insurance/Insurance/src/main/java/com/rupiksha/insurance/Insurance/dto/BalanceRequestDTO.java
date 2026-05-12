package com.rupiksha.insurance.Insurance.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BalanceRequestDTO {

    @NotBlank(message = "Service is required")
    private String service;
}