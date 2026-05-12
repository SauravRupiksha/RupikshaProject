package com.rupiksha.fingpayaeps.faeps.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CashWithdrawalData {

    private String terminalId;

    private String requestTransactionTime; // dd/MM/yyyy HH:mm:ss

    private Double transactionAmount;

    private String transactionStatus;

    private Double balanceAmount;

    private String bankRRN;

    private String transactionType;

    @JsonProperty("FingpayTransactionId")
    private String fingpayTransactionId;

    private String merchantTxnId;

    @JsonProperty("responseCode")
    private String responseCode;
}