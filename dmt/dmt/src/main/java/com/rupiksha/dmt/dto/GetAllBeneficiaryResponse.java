package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class GetAllBeneficiaryResponse {

    @JsonProperty("status_id")
    private Integer statusId;

    @JsonProperty("bene_list")
    private BeneList beneList;

    @Data
    public static class BeneList {
        private List<Beneficiary> data;
    }
}