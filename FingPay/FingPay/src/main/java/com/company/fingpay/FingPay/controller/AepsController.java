package com.company.fingpay.FingPay.controller;





import com.company.fingpay.FingPay.dto.*;
import com.company.fingpay.FingPay.service.AepsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aeps")
public class AepsController {

    private final AepsService aepsService;

    public AepsController(AepsService aepsService) {
        this.aepsService = aepsService;
    }


    /* -------------------------
            2FA AUTH
    -------------------------- */

    @PostMapping("/2fa")
    public ResponseEntity<?> twoFactorAuth(
            @RequestBody TwoFARequest request) throws Exception {

        return ResponseEntity.ok(aepsService.twoFactorAuth(request));
    }


    /* -------------------------
        BALANCE ENQUIRY
    -------------------------- */

    @PostMapping("/balance")
    public ResponseEntity<?> balanceEnquiry(
            @RequestBody BalanceEnquiryRequest request) throws Exception {

        return ResponseEntity.ok(aepsService.balanceEnquiry(request));
    }


    /* -------------------------
        CASH WITHDRAWAL
    -------------------------- */

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(
            @RequestBody CashWithdrawalRequest request) throws Exception {

        return ResponseEntity.ok(aepsService.cashWithdrawal(request));
    }


    /* -------------------------
        MINI STATEMENT
    -------------------------- */

    @PostMapping("/mini-statement")
    public ResponseEntity<?> miniStatement(
            @RequestBody MiniStatementRequest request) throws Exception {

        return ResponseEntity.ok(aepsService.miniStatement(request));
    }


    /* -------------------------
        AADHAAR PAY
    -------------------------- */

    @PostMapping("/aadhaar-pay")
    public ResponseEntity<?> aadhaarPay(
            @RequestBody AadhaarPayRequest request) throws Exception {

        return ResponseEntity.ok(aepsService.aadhaarPay(request));
    }


    /* -------------------------
        CASH DEPOSIT
    -------------------------- */

    @PostMapping("/cash-deposit")
    public ResponseEntity<?> cashDeposit(
            @RequestBody CashDepositRequest request) throws Exception {

        return ResponseEntity.ok(aepsService.cashDeposit(request));
    }


    /* -------------------------
        STATUS CHECK
    -------------------------- */

    @GetMapping("/status")
    public ResponseEntity<?> statusCheck(
            @RequestParam String merchantLoginId,
            @RequestParam String merchantTranId,
            @RequestParam String merchantPassword) throws Exception {

        return ResponseEntity.ok(
                aepsService.statusCheck(
                        merchantLoginId,
                        merchantTranId,
                        merchantPassword
                )
        );
    }


    /* -------------------------
        THREE WAY RECON
    -------------------------- */

    @GetMapping("/recon")
    public ResponseEntity<?> recon(
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
