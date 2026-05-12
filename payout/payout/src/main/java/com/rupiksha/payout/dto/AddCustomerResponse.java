package com.rupiksha.payout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddCustomerResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    private String message;

}