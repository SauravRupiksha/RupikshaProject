package com.rupiksha.fingpayaeps.faeps.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TwoFaData {

    @JsonProperty("fingpayTransactionId")
    private String fingpayTransactionId;


    @JsonProperty("tefPkId")
    private String tefPkId;

    // 🔥 Handle both cases
    @JsonAlias({"bankRRN", "bankRrn"})
    private String bankRrn;

    @JsonAlias({"fpRrn", "fpRRN"})
    private String fpRrn;

    @JsonProperty("stan")
    private String stan;

    @JsonProperty("merchantTranId")
    private String merchantTranId;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("transactionTimestamp")
    private String transactionTimestamp;
    // ===============================
    // 🔐 SAFE HELPERS
    // ===============================

    @JsonIgnore
    public String safeCode() {
        return responseCode == null ? "" : responseCode.trim();
    }

    @JsonIgnore
    public String safeMessage() {
        return responseMessage == null ? "" : responseMessage.trim();
    }

    @JsonIgnore
    public String safeBankRrn() {
        return bankRrn == null ? "" : bankRrn.trim();
    }

    @JsonIgnore
    public String safeFpRrn() {
        return fpRrn == null ? "" : fpRrn.trim();
    }

    // ===============================
    // ✅ BUSINESS LOGIC
    // ===============================

    @JsonIgnore
    public boolean isSuccess() {
        return "00".equalsIgnoreCase(safeCode());
    }

    @JsonIgnore
    public boolean isTfaRequired() {
        return "FP069".equalsIgnoreCase(safeCode());
    }

    @JsonIgnore
    public boolean isFailed() {
        return !isSuccess() && !isTfaRequired();
    }

    // ===============================
    // 🔍 DEBUG
    // ===============================

    @Override
    public String toString() {
        return "TwoFaData{" +
                "responseCode='" + safeCode() + '\'' +
                ", responseMessage='" + safeMessage() + '\'' +
                ", merchantTranId='" + merchantTranId + '\'' +
                ", bankRrn='" + safeBankRrn() + '\'' +
                ", fingpayTransactionId='" + fingpayTransactionId + '\'' +
                '}';
    }
}