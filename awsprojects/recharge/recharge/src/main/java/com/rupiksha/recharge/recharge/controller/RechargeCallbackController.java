package com.rupiksha.recharge.recharge.controller;

import com.rupiksha.recharge.recharge.entity.RechargeTransaction;

import com.rupiksha.recharge.recharge.repository.RechargeTransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recharge")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class RechargeCallbackController {

    private final RechargeTransactionRepository repository;

    @GetMapping("/callback")
    public String rechargeCallback(

            @RequestParam String ResponseStatus,

            @RequestParam String OperatorTxnID,

            @RequestParam String OrderNo,

            @RequestParam String MerTxnID,

            @RequestParam String AccountNo

    ) {

        log.info(
                "Recharge Callback Received : {}",
                MerTxnID
        );

        RechargeTransaction txn =
                repository
                .findByMerchantRefNo(MerTxnID)
                .orElse(null);

        if(txn != null){

            txn.setResponseStatus(
                    ResponseStatus
            );

            txn.setOperatorTxnId(
                    OperatorTxnID
            );

            txn.setOrderNo(
                    OrderNo
            );

            repository.save(txn);
        }

        return "SUCCESS";
    }
}