package com.company.fingpay.FingPay.repository;


import com.company.fingpay.FingPay.entity.AepsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AepsTransactionRepository
        extends JpaRepository<AepsTransaction, Long> {

    Optional<AepsTransaction> findByMerchantTranId(String merchantTranId);

    List<AepsTransaction> findByStatus(String status);

    List<AepsTransaction> findByRetryCountLessThan(Integer retryCount);

    List<AepsTransaction> findByStatusAndRetryCountLessThan(
            String status,
            Integer retryCount
    );

    List<AepsTransaction> findByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    List<AepsTransaction> findTop50ByStatusAndRetryCountLessThanOrderByCreatedAtAsc(
            String status,
            Integer retryCount
    );

}