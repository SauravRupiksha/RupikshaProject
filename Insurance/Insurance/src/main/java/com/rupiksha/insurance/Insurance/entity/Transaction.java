package com.rupiksha.insurance.Insurance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions",
        indexes = {
                @Index(name = "idx_status", columnList = "status"),
                @Index(name = "idx_merchant_ref", columnList = "merchantRefNo"),
                @Index(name = "idx_account_number", columnList = "accountNumber"),
                @Index(name = "idx_mobile_no", columnList = "mobileNo"),
                @Index(name = "idx_order_id", columnList = "orderId")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 Unique identifiers
    @Column(unique = true, nullable = false, length = 50)
    private String merchantRefNo;

    @Column(unique = true, length = 50)
    private String orderId;

    @Column(unique = true, length = 50)
    private String operatorTxnId;

    // 🔥 Customer details
    @Column(length = 30)
    private String accountNumber;

    @Column(length = 15)
    private String mobileNo;

    @Column(length = 100)
    private String consumerName;

    @Column(length = 20)
    private String dueDate;

    // 🔥 Service details
    @Column(length = 20)
    private String operatorCode;

    @Column(length = 20)
    private String serviceType;

    // 🔥 Financial
    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    // 🔥 Status tracking
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Status status;

    // 🔥 Retry + callback tracking
    @Column
    private Integer retryCount = 0;

    @Column
    private Boolean callbackReceived = false;

    // 🔥 Logging fields
    @Column(length = 1000)
    private String description;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String providerResponse;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String providerRequest;

    // 🔥 Timestamps
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // 🔥 Lifecycle hooks
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = Status.FETCH_INITIATED;
        }

        if (this.serviceType == null) {
            this.serviceType = "INS";
        }

        if (this.retryCount == null) {
            this.retryCount = 0;
        }

        if (this.callbackReceived == null) {
            this.callbackReceived = false;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}