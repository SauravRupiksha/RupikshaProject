package com.rupiksha.cms.service;

import com.rupiksha.cms.dto.CmsCallbackRequest;
import com.rupiksha.cms.dto.CmsCallbackResponse;
import com.rupiksha.cms.entity.CmsCallbackTransaction;
import com.rupiksha.cms.repository.CmsCallbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CmsCallbackService {

    private final CmsCallbackRepository repository;

    public CmsCallbackService(CmsCallbackRepository repository) {
        this.repository = repository;
    }

    public CmsCallbackResponse processCallback(
            CmsCallbackRequest request){

        log.info("CMS Callback Received: {}", request);

        CmsCallbackTransaction entity = new CmsCallbackTransaction();

        entity.setStatusId(request.getStatus_id());
        entity.setTxnid(request.getTxnid());
        entity.setAmount(request.getAmount());
        entity.setMessage(request.getMessage());
        entity.setUserId(request.getUser_id());
        entity.setOperatorRefId(request.getOperator_ref_id());
        entity.setType(request.getType());
        entity.setApiAgentId(request.getApi_agent_id());
        entity.setTimestamp(request.getTimestamp());
        entity.setClientRefId(request.getClient_ref_id());
        entity.setProviderName(request.getProvider_name());
        entity.setProviderId(request.getProvider_id());
        entity.setCaNo(request.getCa_no());
        entity.setDescription(request.getDescription());

        repository.save(entity);

        return new CmsCallbackResponse(
                "1",
                "success",
                request.getClient_ref_id()
        );
    }

}