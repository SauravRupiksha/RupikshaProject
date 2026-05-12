package com.rupiksha.cmsart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Cms5StatusResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    private String message;

    private String utr;

}