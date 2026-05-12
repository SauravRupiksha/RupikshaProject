package com.rupiksha.payout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetCustomerResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    private String message;

    @JsonProperty("f_name")
    private String firstName;

    @JsonProperty("l_name")
    private String lastName;

    private String limit;

    private String wallet1;

    @JsonProperty("cashout_remaining")
    private String cashoutRemaining;

    private String currency;

    private String w1;

}