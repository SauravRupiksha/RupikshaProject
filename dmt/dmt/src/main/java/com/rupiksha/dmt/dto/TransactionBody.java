package com.rupiksha.dmt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionBody {

    private String channel;

    @JsonProperty("clientUniqueID")
    private String clientUniqueID;

    private String beneIFSCCode;
    private String beneAccountNo;
    private String beneName;
    private String customerName;
    private String amount;

    @JsonProperty("panNumber")
    private String panNumber;

    @JsonProperty("pinCode")
    private String pinCode;

    private String customerMobileNo;
    private String bankName;
    private String token;
}
