package com.rupiksha.dmt.service;

import com.rupiksha.dmt.dto.BankDownCallbackRequest;
import com.rupiksha.dmt.entity.BankStatus;
import com.rupiksha.dmt.repository.BankStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BankStatusService {

    @Autowired
    private BankStatusRepository repository;

    public void saveBankStatus(
            BankDownCallbackRequest request){

        BankStatus status = new BankStatus();

        status.setBankDownCode(request.getBankDownCode());
        status.setBankName(request.getBankName());
        status.setBankStatus(request.getBankStatus());
        status.setUserId(request.getUserId());
        status.setType(request.getType());
        status.setCreatedAt(LocalDateTime.now());

        repository.save(status);
    }
}