package com.rupiksha.cms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cms_callback_transaction")
public class CmsCallbackTransaction {

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

    @Column(length = 500)
    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();

}