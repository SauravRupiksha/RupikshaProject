package com.rupiksha.insurance.Insurance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "operator",
        indexes = {
                @Index(name = "idx_operator_code", columnList = "code"),
                @Index(name = "idx_service_type", columnList = "serviceType")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 Operator details
    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 20)
    private String code;

    // 🔥 Service type (INS, EB, DTH, etc.)
    @Column(nullable = false, length = 20)
    private String serviceType;

    // 🔥 Active flag (important for production)
    @Column(nullable = false)
    private Boolean active = true;

    // 🔥 Optional metadata
    @Column(length = 200)
    private String description;

    // 🔥 Timestamps
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // 🔥 Lifecycle hooks
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.active == null) {
            this.active = true;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}