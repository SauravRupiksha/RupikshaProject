package com.rupiksha.cmsart.repository;

import com.rupiksha.cmsart.entity.Cms5CallbackTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Cms5CallbackRepository
        extends JpaRepository<Cms5CallbackTransaction, Long> {

    Optional<Cms5CallbackTransaction> findByTxnid(String txnid);

}