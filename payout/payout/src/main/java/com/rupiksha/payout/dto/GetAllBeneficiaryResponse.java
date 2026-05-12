package com.rupiksha.payout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetAllBeneficiaryResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    @JsonProperty("bene_list")
    private BeneficiaryList beneList;

}