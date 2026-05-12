package com.rupiksha.cms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class C3TransactionRequest {

    @NotBlank(message = "Mobile number is required")
    private String mobile_number;

    @NotBlank(message = "Agent ID is required")
    private String aeps_agent_id;

    @NotBlank(message = "Agent PIN is required")
    private String aeps_agent_pin;

    private String latitude;

    @NotBlank(message = "Longitude is required")
    private String longitude;

}