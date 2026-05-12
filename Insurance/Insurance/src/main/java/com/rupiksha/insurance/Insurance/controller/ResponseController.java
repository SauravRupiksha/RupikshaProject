package com.rupiksha.insurance.Insurance.controller;

import com.rupiksha.insurance.Insurance.entity.Transaction;
import com.rupiksha.insurance.Insurance.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bbps")
public class ResponseController {

    private final TransactionRepository transactionRepository;

    public ResponseController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/callback")
    public String callback(

            @RequestParam(required = false) String ResponseStatus,
            @RequestParam(required = false) String OperatorTxnID,
            @RequestParam(required = false) String OrderNo,
            @RequestParam(required = false) String MerTxnID,
            @RequestParam(required = false) String AccountNo
    ) {

        System.out.println("===== CALLBACK RECEIVED =====");
        System.out.println("TxnID : " + MerTxnID);
        System.out.println("Status: " + ResponseStatus);

        if (MerTxnID == null || MerTxnID.isBlank()) {
            return "INVALID_TXN";
        }

        Transaction txn = transactionRepository
                .findByMerchantRefNo(MerTxnID)
                .orElse(null);

        if (txn == null) {
            System.out.println("Transaction Not Found");
            return "NOT_FOUND";
        }

        // Prevent duplicate success update
        if ("SUCCESS".equalsIgnoreCase(txn.getStatus())) {
            return "ALREADY_UPDATED";
        }

        String finalStatus = mapStatus(ResponseStatus);

        txn.setStatus(finalStatus);
        txn.setOrderId(OrderNo);

        txn.setDescription(
                "OperatorTxnID=" + OperatorTxnID +
                        ", RefNo=" + AccountNo
        );

        transactionRepository.save(txn);

        System.out.println("Transaction Updated");

        return "SUCCESS";
    }

    // BBPS STATUS MAPPING
    private String mapStatus(String status) {

        if (status == null) return "UNKNOWN";

        switch (status.toUpperCase()) {

            case "TXN":
            case "SUCCESS":
                return "SUCCESS";

            case "TUP":
                return "PENDING";

            case "TRP":
                return "REFUND";

            default:
                return "FAILED";
        }
    }
}
