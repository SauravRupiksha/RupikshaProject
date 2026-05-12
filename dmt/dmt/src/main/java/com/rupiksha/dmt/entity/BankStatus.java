package com.rupiksha.dmt.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_status")
@Data
public class BankStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_down_code")
    private String bankDownCode;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_status")
    private String bankStatus;

    @Column(name = "user_id")
    private String userId;

    private String type;

    private LocalDateTime createdAt;
}