package com.rupiksha.payout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteBeneficiaryResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    private String message;

    @JsonProperty("deleted_bene_id")
    private String deletedBeneId;

}