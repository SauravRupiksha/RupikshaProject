package com.rupiksha.fingpayaeps.faeps.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwoFaResponse {

    @JsonProperty("status")
    private boolean status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private TwoFaData data;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("statusCode")
    private Long statusCode;

    // ================= SAFE =================

    @JsonIgnore
    public long safeStatusCode() {
        return statusCode == null ? -1 : statusCode;
    }

    @JsonIgnore
    public boolean hasData() {
        return data != null;
    }

    @JsonIgnore
    public String safeResponseCode() {

        if (data != null && data.getResponseCode() != null && !data.getResponseCode().isBlank()) {
            return data.getResponseCode().trim();
        }

        if (responseCode != null && !responseCode.isBlank()) {
            return responseCode.trim();
        }

        return "";
    }

    @JsonIgnore
    public String safeResponseMessage() {

        if (data != null && data.getResponseMessage() != null && !data.getResponseMessage().isBlank()) {
            return data.getResponseMessage().trim();
        }

        if (responseMessage != null && !responseMessage.isBlank()) {
            return responseMessage.trim();
        }

        if (message != null) {
            return message.trim();
        }

        return "";
    }

    // ================= BUSINESS =================

    @JsonIgnore
    public boolean isSuccess() {
        return "00".equals(safeResponseCode());
    }

    @JsonIgnore
    public boolean isFailed() {
        return !isSuccess();
    }

    @JsonIgnore
    public TwoFaData safeData() {
        return data != null ? data : new TwoFaData();
    }

    // ================= DEBUG =================

    @JsonIgnore
    public String debugSummary() {
        return "status=" + status +
                ", statusCode=" + safeStatusCode() +
                ", code=" + safeResponseCode() +
                ", message=" + safeResponseMessage();
    }
}