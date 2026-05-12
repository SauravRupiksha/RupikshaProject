package com.rupiksha.cmsart.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "cms5_callback_transaction")
@Data
public class Cms5CallbackTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statusId;

    private String txnid;

    private String amount;

    private String message;

    private String userId;

    private String operatorRefId;

    private String type;

    private String apiAgentId;

    private String timestamp;

    private String clientRefId;

    private String providerName;

    private String providerId;

    private String caNo;

    @Column(length = 1000)
    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();

}