package com.rupiksha.fingpayaeps.faeps.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TwoFaCardnumberORUID {

    @JsonProperty("adhaarNumber")
    private String adhaarNumber;

    @Builder.Default
    @JsonProperty("indicatorforUID")
    private int indicatorforUID = 0;

    @JsonProperty("nationalBankIdentificationNumber")
    private String nationalBankIdentificationNumber;

    @JsonIgnore
    public boolean isValid() {
        return adhaarNumber != null
                && adhaarNumber.length() == 12
                && nationalBankIdentificationNumber != null
                && indicatorforUID == 0;
    }
}