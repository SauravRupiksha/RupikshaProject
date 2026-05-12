package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Beneficiary {

    @JsonProperty("bene_id")
    private Integer beneId;

    @JsonProperty("recipient_name")
    private String recipientName;

    private String account;
    private String ifsc;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("bank_status")
    private String bankStatus;
}