package com.rupiksha.recharge.recharge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "recharge_transaction")
public class RechargeTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mobileNo;

    private String amount;

    private String operatorCode;

    private String serviceType;

    private String merchantRefNo;

    private String responseStatus;

    private String operatorTxnId;

    private String orderNo;
}