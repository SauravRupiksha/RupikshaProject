package com.rupiksha.fingpayaeps.faeps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardnumberORUID {

    // ✅ API ke liye adhaarNumber, code me correct spelling
    @JsonProperty("adhaarNumber")
    private String aadhaarNumber;

    @JsonProperty("indicatorforUID")
    @Min(0)
    @Max(2)
    private int indicatorforUID;

    @JsonProperty("nationalBankIdentificationNumber")
    private String nationalBankIdentificationNumber;

    // ✅ Only if VID use
    @JsonProperty("virtualId")
    private String virtualId;
}