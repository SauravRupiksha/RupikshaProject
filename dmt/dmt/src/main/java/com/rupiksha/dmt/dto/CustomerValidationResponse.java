package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerValidationResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    private String message;

    private String token;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private Integer limit;
    private String wallet1;
    private String wallet2;
    private String currency;
    private String w1;
    private String w2;

}