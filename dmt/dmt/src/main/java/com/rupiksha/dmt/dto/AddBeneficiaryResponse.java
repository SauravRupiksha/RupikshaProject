package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddBeneficiaryResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    private String message;

    @JsonProperty("bene_id")
    private Integer beneId;

    private String data;
}