package com.company.fingpay.FingPay.controller;


import com.company.fingpay.FingPay.dto.*;
import com.company.fingpay.FingPay.service.AepsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aeps")
public class AepsController {

    private final AepsService aepsService;

    public AepsController(AepsService aepsService) {
        this.aepsService = aepsService;
    }

    @PostMapping("/2fa")
    public ResponseEntity<String> twoFactorAuth(
            @RequestBody @Valid TwoFARequest request) throws Exception {

        return ResponseEntity.ok(aepsService.twoFactorAuth(request));
    }

    @PostMapping("/balance")
    public ResponseEntity<String> balanceEnquiry(
            @RequestBody @Valid BalanceEnquiryRequest request) throws Exception {

        return ResponseEntity.ok(aepsService.balanceEnquiry(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(
            @RequestBody @Valid CashWithdrawalRequest request) throws Exception {

        return ResponseEntity.ok(aepsService.cashWithdrawal(request));
    }

    @PostMapping("/mini-statement")
    public ResponseEntity<String> miniStatement(
            @RequestBody @Valid MiniStatementRequest request) throws Exception {

        return ResponseEntity.ok(aepsService.miniStatement(request));
    }

    @PostMapping("/aadhaar-pay")
    public ResponseEntity<String> aadhaarPay(
            @RequestBody @Valid AadhaarPayRequest request) throws Exception {

        return ResponseEntity.ok(aepsService.aadhaarPay(request));
    }

    @PostMapping("/cash-deposit")
    public ResponseEntity<String> cashDeposit(
            @RequestBody @Valid CashDepositRequest request) throws Exception {

        return ResponseEntity.ok(aepsService.cashDeposit(request));
    }

    @PostMapping("/status")
    public ResponseEntity<String> statusCheck(
            @RequestBody StatusCheckRequest request) throws Exception {

        return ResponseEntity.ok(
                aepsService.statusCheck(
                        request.getMerchantLoginId(),
                        request.getMerchantTranId(),
                        request.getHash()
                )
        );
    }

    @GetMapping("/recon")
    public ResponseEntity<String> recon(
            @RequestParam String merchantLoginId,
            @RequestParam String fromDate,
            @RequestParam String toDate) throws Exception {

        return ResponseEntity.ok(
                aepsService.threeWayRecon(
                        merchantLoginId,
                        fromDate,
                        toDate
                )
        );
    }
}