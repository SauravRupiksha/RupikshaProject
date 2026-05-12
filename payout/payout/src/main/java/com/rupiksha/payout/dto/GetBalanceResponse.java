package com.rupiksha.payout.dto;

import lombok.Data;

@Data
public class GetBalanceResponse {

    private Integer status;
    private Double balance;
    private String message;

}