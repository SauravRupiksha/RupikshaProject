package com.rupiksha.insurance.Insurance.controller;




import com.rupiksha.insurance.Insurance.service.VenusPaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bbps")
public class BillPaymentController {

    private final VenusPaymentService paymentService;

    public BillPaymentController(VenusPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public String pay(
            @RequestParam String accountNumber,
            @RequestParam String mobileNo,
            @RequestParam String amount,
            @RequestParam String operatorCode,
            @RequestParam String serviceType,   // EB / INS
            @RequestParam String orderId,
            @RequestParam String merchantRefNo
    ) {

        // Account / Policy validation
        if (accountNumber == null || accountNumber.isBlank()) {
            return "INVALID_ACCOUNT_NUMBER";
        }

        // Mobile validation
        if (mobileNo == null || !mobileNo.matches("\\d{10}")) {
            return "INVALID_MOBILE";
        }

        // Amount validation
        if (amount == null || amount.isBlank()) {
            return "INVALID_AMOUNT";
        }

        // Operator validation
        if (operatorCode == null || operatorCode.isBlank()) {
            return "INVALID_OPERATOR";
        }

        // ServiceType validation
        if (serviceType == null || serviceType.isBlank()) {
            return "INVALID_SERVICE_TYPE";
        }

        // Order validation
        if (orderId == null || orderId.isBlank()) {
            return "INVALID_ORDER_ID";
        }

        // Transaction validation
        if (merchantRefNo == null || merchantRefNo.isBlank()) {
            return "INVALID_TXN_ID";
        }

        try {
            return paymentService.payBill(
                    accountNumber,
                    mobileNo,
                    amount,
                    orderId,
                    merchantRefNo,
                    operatorCode,
                    serviceType
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "PAYMENT_FAILED";
        }
    }
}
