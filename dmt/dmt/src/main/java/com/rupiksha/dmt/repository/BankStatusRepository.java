package com.rupiksha.dmt.repository;

import com.rupiksha.dmt.entity.BankStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankStatusRepository 
        extends JpaRepository<BankStatus, Long> {
}