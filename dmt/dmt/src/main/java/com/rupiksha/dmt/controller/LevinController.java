package com.rupiksha.dmt.controller;



import com.rupiksha.dmt.dto.*;
import com.rupiksha.dmt.service.LevinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/levin")
@RequiredArgsConstructor
public class LevinController {

    private final LevinService levinService;

    @PostMapping("/validate-customer")
    public CustomerValidationResponse validateCustomer(
            @RequestParam String mobile){

        return levinService.validateCustomer(mobile);
    }

    @PostMapping("/validate-aadhaar")
    public AadhaarValidationResponse validateAadhaar(
            @RequestParam String mobile,
            @RequestParam String aadhar,
            @RequestParam String token){

        return levinService.validateAadhaar(mobile, aadhar, token);
    }

    @PostMapping("/send-otp")
    public SendOtpResponse sendOtp(
            @RequestParam String mobile){

        return levinService.sendOtp(mobile);
    }
    @PostMapping("/verify-otp")
    public VerifyOtpResponse verifyOtp(
            @RequestParam String mobile,
            @RequestParam String otp,
            @RequestParam String token){

        return levinService.verifyOtp(mobile,otp,token);
    }

    @PostMapping("/perform-ekyc")
    public PerformEKYCResponse performEKYC(
            @RequestBody PerformEKYCBody request){

        return levinService.performEKYC(
                request.getMobile(),
                request.getToken(),
                request.getPidata());
    }

    @PostMapping("/add-beneficiary")
    public AddBeneficiaryResponse addBeneficiary(
            @RequestBody AddBeneficiaryBody request){

        return levinService.addBeneficiary(
                request.getMobile(),
                request.getBeneName(),
                request.getNumber(),
                request.getBankAccount(),
                request.getBankName(),
                request.getIfsc(),
                request.getPartnerSubId()
        );
    }

    @PostMapping("/get-all-beneficiary")
    public GetAllBeneficiaryResponse getAllBeneficiary(
            @RequestParam String mobile){

        return levinService.getAllBeneficiary(mobile);
    }

    @PostMapping("/send-txn-otp")
    public TransactionOtpResponse sendTxnOtp(
            @RequestBody TransactionOtpBody request){

        return levinService.sendTxnOtp(
                request.getMobile(),
                request.getAmount()
        );
    }

    @PostMapping("/verify-txn-otp")
    public VerifyTxnOtpResponse verifyTxnOtp(
            @RequestBody VerifyTxnOtpBody request){

        return levinService.verifyTxnOtp(
                request.getMobile(),
                request.getOtp(),
                request.getToken()
        );
    }
    @PostMapping("/transaction")
    public TransactionResponse transaction(
            @RequestBody TransactionBody request){

        return levinService.transaction(request);
    }

    @PostMapping("/transaction-status")
    public TransactionStatusResponse transactionStatus(
            @RequestBody TransactionStatusBody request){

        return levinService.transactionStatus(
                request.getMobile(),
                request.getTxnid(),
                request.getClientRefId()
        );
    }
    @PostMapping("/account-verification")
    public AccountVerificationResponse accountVerification(
            @RequestBody AccountVerificationBody request){

        return levinService.accountVerification(request);
    }

    @PostMapping("/wallet-limit")
    public WalletLimitResponse walletLimit(
            @RequestBody WalletLimitBody request){

        return levinService.getWalletLimit(request);
    }



}