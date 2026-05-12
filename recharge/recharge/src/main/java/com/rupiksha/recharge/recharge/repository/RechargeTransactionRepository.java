package com.rupiksha.recharge.recharge.repository;

import com.rupiksha.recharge.recharge.entity.RechargeTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RechargeTransactionRepository
        extends JpaRepository<RechargeTransaction, Long> {

    Optional<RechargeTransaction>
    findByMerchantRefNo(String merchantRefNo);
}