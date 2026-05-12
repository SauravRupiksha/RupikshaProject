package com.rupiksha.dmt.dto;

import lombok.Data;

@Data
public class TransactionStatusBody {

    private String mobile;
    private String txnid;
    private String clientRefId;
}