package com.rupiksha.payout.controller;

import com.rupiksha.payout.dto.*;
import com.rupiksha.payout.service.LevinDmtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dmt")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LevinDmtController {

    private final LevinDmtService service;

    @PostMapping("/get-customer")
    public ResponseEntity<ApiResponse<?>> getCustomer(
            @RequestBody GetCustomerRequest request
    ) {

        GetCustomerResponse response =
                service.getCustomer(request);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Customer fetched successfully")
                        .data(response)
                        .status(200)
                        .build()
        );
    }

    @PostMapping("/add-customer")
    public ResponseEntity<?> addCustomer(
            @RequestBody AddCustomerRequest request
    ) {

        return ResponseEntity.ok(
                service.addCustomer(request)
        );
    }

    @PostMapping("/add-customer-confirm")
    public ResponseEntity<?> addCustomerConfirm(
            @RequestBody AddCustomerConfirmRequest request
    ) {

        return ResponseEntity.ok(
                service.addCustomerConfirm(request)
        );
    }

    @PostMapping("/add-beneficiary")
    public ResponseEntity<?> addBeneficiary(
            @RequestBody AddBeneficiaryRequest request
    ) {

        return ResponseEntity.ok(
                service.addBeneficiary(request)
        );
    }
    @PostMapping("/get-all-beneficiary")
    public ResponseEntity<?> getAllBeneficiary(
            @RequestParam String mobileNumber
    ) {

        return ResponseEntity.ok(
                service.getAllBeneficiary(mobileNumber)
        );
    }
    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(
            @RequestBody TransactionRequest request
    ) {

        return ResponseEntity.ok(
                service.transaction(request)
        );
    }
    @PostMapping("/transaction-status")
    public ResponseEntity<?> transactionStatus(
            @RequestBody TransactionStatusRequest request
    ) {

        return ResponseEntity.ok(
                service.transactionStatus(request)
        );
    }

    @PostMapping("/generate-otp")
    public ResponseEntity<?> generateOtp(
            @RequestBody GenerateOtpRequest request
    ) {

        return ResponseEntity.ok(
                service.generateOtp(request)
        );
    }

    @PostMapping("/account-verification")
    public ResponseEntity<?> accountVerification(
            @RequestBody AccountVerificationRequest request
    ) {

        return ResponseEntity.ok(
                service.accountVerification(request)
        );
    }
    @PostMapping("/delete-beneficiary")
    public ResponseEntity<?> deleteBeneficiary(
            @RequestBody DeleteBeneficiaryRequest request
    ) {

        return ResponseEntity.ok(
                service.deleteBeneficiary(request)
        );
    }
    @PostMapping("/refund-otp")
    public ResponseEntity<?> refundOtp(
            @RequestBody RefundOtpRequest request
    ) {

        return ResponseEntity.ok(
                service.refundOtp(request)
        );
    }
    @PostMapping("/refund-success")
    public ResponseEntity<?> refundSuccess(
            @RequestBody RefundSuccessRequest request
    ) {

        return ResponseEntity.ok(
                service.refundSuccess(request)
        );
    }

    @PostMapping("/get-balance")
    public ResponseEntity<?> getBalance(
            @RequestBody GetBalanceRequest request
    ) {

        return ResponseEntity.ok(
                service.getBalance(request)
        );
    }

}