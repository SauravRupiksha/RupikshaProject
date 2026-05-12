package com.rupiksha.fingpayaeps.faeps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TwoFaCaptureResponse {

    @JsonProperty("PidDatatype")
    private String pidDatatype;

    @JsonProperty("Piddata")
    private String piddata;

    @JsonProperty("ci")
    private String ci;

    @JsonProperty("dc")
    private String dc;

    @JsonProperty("dpID")
    private String dpID;

    @JsonProperty("errCode")
    private String errCode;

    @JsonProperty("errInfo")
    private String errInfo;

    @JsonProperty("fCount")
    private String fCount;

    @JsonProperty("fType")
    private String fType;

    @JsonProperty("qScore")
    private String qScore;

    @JsonProperty("hmac")
    private String hmac;

    @JsonProperty("sessionKey")
    private String sessionKey;

    @JsonProperty("mc")
    private String mc;

    @JsonProperty("mi")
    private String mi;

    @JsonProperty("nmPoints")
    private String nmPoints;

    @JsonProperty("rdsID")
    private String rdsID;

    @JsonProperty("rdsVer")
    private String rdsVer;
}