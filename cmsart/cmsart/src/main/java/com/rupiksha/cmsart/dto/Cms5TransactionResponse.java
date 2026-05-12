package com.rupiksha.cmsart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Cms5TransactionResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    private String message;

    @JsonProperty("redirect_url")
    private String redirectUrl;

}