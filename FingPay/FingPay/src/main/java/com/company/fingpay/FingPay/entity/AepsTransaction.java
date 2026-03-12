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

    private String fingpayTransactionId;

    private String bankRrn;

    private String stan;

    private String transactionType;

    private String aadhaarLast4;

    @Column(precision = 12, scale = 2)
    private BigDecimal amount;

    private String status;

    private String responseCode;

    private String responseMessage;

    private String deviceImei;

    private Double latitude;

    private Double longitude;

    private String mobileNumber;

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
