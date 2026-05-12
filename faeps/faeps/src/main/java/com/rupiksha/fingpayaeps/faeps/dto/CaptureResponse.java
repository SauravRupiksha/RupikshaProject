package com.rupiksha.fingpayaeps.faeps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaptureResponse {

    @JsonProperty("errCode")
    private String errCode;

    @JsonProperty("errInfo")
    private String errInfo;

    @JsonProperty("fCount")
    private String fCount;

    @JsonProperty("fType")
    private String fType;

    @JsonProperty("iCount")
    private String iCount;

    @JsonProperty("iType")
    private String iType;

    @JsonProperty("pCount")
    private String pCount;

    @JsonProperty("pType")
    private String pType;

    @JsonProperty("nmPoints")
    private String nmPoints;

    @JsonProperty("qScore")
    private String qScore;

    @JsonProperty("dpID")
    private String dpID;

    @JsonProperty("rdsID")
    private String rdsID;

    @JsonProperty("rdsVer")
    private String rdsVer;

    @JsonProperty("dc")
    private String dc;

    @JsonProperty("mi")
    private String mi;

    @JsonProperty("mc")
    private String mc;

    @JsonProperty("ci")
    private String ci;

    @JsonProperty("sessionKey")
    private String sessionKey;

    @JsonProperty("hmac")
    private String hmac;

    @JsonProperty("PidDatatype")
    private String pidDatatype;

    @JsonProperty("Piddata")
    private String piddata;
}