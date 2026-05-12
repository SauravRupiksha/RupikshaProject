package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PerformEKYCResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    private String message;
    private String firstName;
    private String lastName;
    private String pincode;
    private String city;
}