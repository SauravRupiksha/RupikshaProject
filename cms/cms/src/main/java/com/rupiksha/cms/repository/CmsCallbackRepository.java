package com.rupiksha.cms.repository;

import com.rupiksha.cms.entity.CmsCallbackTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CmsCallbackRepository
        extends JpaRepository<CmsCallbackTransaction, Long> {

    Optional<CmsCallbackTransaction> findByTxnid(String txnid);

    Optional<CmsCallbackTransaction> findByClientRefId(String clientRefId);

    List<CmsCallbackTransaction> findByStatusId(String statusId);

}