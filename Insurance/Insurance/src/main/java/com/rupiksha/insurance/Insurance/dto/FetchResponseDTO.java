package com.rupiksha.insurance.Insurance.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FetchResponseDTO {

    private String consumerName;
    private BigDecimal amount;
    private String dueDate;
    private String orderId;
}