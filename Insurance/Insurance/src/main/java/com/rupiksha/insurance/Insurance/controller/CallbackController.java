package com.rupiksha.insurance.Insurance.controller;

import com.rupiksha.insurance.Insurance.entity.Status;
import com.rupiksha.insurance.Insurance.entity.Transaction;
import com.rupiksha.insurance.Insurance.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bbps")
public class CallbackController {

    private static final Logger log =
            LoggerFactory.getLogger(CallbackController.class);

    private final TransactionRepository transactionRepository;

    public CallbackController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/callback")
    @Transactional
    public ResponseEntity<String> callback(

            @RequestParam(required = false) String ResponseStatus,
            @RequestParam(required = false) String OperatorTxnID,
            @RequestParam(required = false) String OrderNo,
            @RequestParam(required = false) String MerTxnID,
            @RequestParam(required = false) String AccountNo
    ) {

        log.info("📥 Callback received: MerTxnID={}, Status={}", MerTxnID, ResponseStatus);

        // ❗ Validate MerTxnID
        if (MerTxnID == null || MerTxnID.isBlank()) {
            log.warn("⚠️ Invalid callback: MerTxnID is null/blank");
            return ResponseEntity.ok("INVALID");
        }

        Transaction txn = transactionRepository
                .findByMerchantRefNo(MerTxnID)
                .orElse(null);

        // ❗ Transaction not found
        if (txn == null) {
            log.warn("⚠️ Transaction not found for MerTxnID={}", MerTxnID);
            return ResponseEntity.ok("TXN_NOT_FOUND");
        }

        Status newStatus = mapStatus(ResponseStatus);

        // ❗ Prevent downgrade (SUCCESS → FAILED)
        if (txn.getStatus() == Status.SUCCESS && newStatus != Status.SUCCESS) {
            log.warn("⛔ Ignoring downgrade callback for MerTxnID={}", MerTxnID);
            return ResponseEntity.ok("IGNORED");
        }

        // ❗ Idempotency (already final)
        if (txn.getStatus() == Status.SUCCESS || txn.getStatus() == Status.REFUND) {
            log.info("🔁 Already processed for MerTxnID={}", MerTxnID);
            return ResponseEntity.ok("ALREADY_PROCESSED");
        }

        try {

            // ✅ Update status
            txn.setStatus(newStatus);

            // ✅ Set OrderId only if empty
            if (txn.getOrderId() == null && OrderNo != null) {
                txn.setOrderId(OrderNo);
            }

            // ✅ Set OperatorTxnId safely
            if (OperatorTxnID != null && !OperatorTxnID.isBlank()) {
                txn.setOperatorTxnId(OperatorTxnID);
            }

            // ✅ Mask account number
            String maskedAccount = mask(AccountNo);

            txn.setDescription(
                    "OperatorTxnID=" + OperatorTxnID +
                            ", AccountNo=" + maskedAccount
            );

            transactionRepository.save(txn);

            log.info("✅ Transaction updated: MerTxnID={}, Status={}", MerTxnID, newStatus);

            return ResponseEntity.ok("SUCCESS");

        } catch (Exception e) {

            log.error("❌ Callback processing error for MerTxnID={}", MerTxnID, e);

            txn.setStatus(Status.FAILED);
            transactionRepository.save(txn);

            return ResponseEntity.ok("FAILED");
        }
    }

    // 🔥 Status mapping (BBPS compatible)
    private Status mapStatus(String status) {

        if (status == null) return Status.FAILED;

        switch (status.toUpperCase()) {

            case "TXN":
            case "SUCCESS":
                return Status.SUCCESS;

            case "TUP":
                return Status.PENDING;

            case "TRP":
                return Status.REFUND;

            default:
                log.warn("⚠️ Unknown status received from Venus: {}", status);
                return Status.FAILED;
        }
    }

    // 🔒 Mask sensitive data
    private String mask(String value) {
        if (value == null || value.length() < 4) return value;
        return "XXXXXX" + value.substring(value.length() - 4);
    }
}