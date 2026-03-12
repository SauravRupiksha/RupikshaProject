package com.company.fingpay.FingPay.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "aeps_transaction")
public class AepsTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String merchantTranId;

    @Column(length = 50)
    private String fingpayTransactionId;

    @Column(length = 30)
    private String bankRrn;

    @Column(length = 20)
    private String stan;

    @Column(length = 10)
    private String transactionType;

    @Column(length = 4)
    private String aadhaarLast4;

    @Column(precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(length = 20)
    private String status;

    @Column(length = 20)
    private String responseCode;

    @Column(length = 200)
    private String responseMessage;

    @Column(length = 20)
    private String deviceImei;

    private Double latitude;

    private Double longitude;

    @Column(length = 15)
    private String mobileNumber;

    @Column(length = 50)
    private String bankName;

    private Integer retryCount = 0;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}