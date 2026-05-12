package com.rupiksha.fingpayaeps.faeps.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CashWithdrawalResponse {

    private boolean status;

    private String message;

    private CashWithdrawalData data;

    private Long statusCode;

    // 🔥 only for debugging (do not expose in API)
    private Object rawResponse;
}