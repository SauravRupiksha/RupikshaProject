package com.rupiksha.insurance.Insurance.controller;




import com.rupiksha.insurance.Insurance.entity.Transaction;
import com.rupiksha.insurance.Insurance.repository.TransactionRepository;
import com.rupiksha.insurance.Insurance.service.VenusFetchService;
import com.rupiksha.insurance.Insurance.util.MerchantRefGenerator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bbps")
public class FetchBillController {

    private final VenusFetchService fetchService;
    private final TransactionRepository transactionRepository;

    public FetchBillController(
            VenusFetchService fetchService,
            TransactionRepository transactionRepository) {

        this.fetchService = fetchService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/fetch")
    public String fetch(
            @RequestParam String accountNumber,
            @RequestParam String mobileNo,
            @RequestParam String operatorCode,
            @RequestParam String serviceType,   // EB / INS
            @RequestParam(required = false) String subdiv,
            @RequestParam(required = false) String email   // ✅ ADD THIS
    ) {

        if (accountNumber == null || accountNumber.isBlank()) {
            return "INVALID_ACCOUNT_NUMBER";
        }

        if (mobileNo == null || !mobileNo.matches("\\d{10}")) {
            return "INVALID_MOBILE";
        }

        if (operatorCode == null || operatorCode.isBlank()) {
            return "INVALID_OPERATOR";
        }

        if (serviceType == null || serviceType.isBlank()) {
            return "INVALID_SERVICE_TYPE";
        }

        String merchantRefNo = MerchantRefGenerator.generate();

        Transaction txn = Transaction.builder()
                .merchantRefNo(merchantRefNo)
                .accountNumber(accountNumber)
                .mobileNo(mobileNo)
                .operatorCode(operatorCode)
                .serviceType(serviceType)
                .status("FETCH_INITIATED")
                .build();

        transactionRepository.save(txn);

        return fetchService.fetchBill(
                accountNumber,
                mobileNo,
                subdiv,
                merchantRefNo,
                operatorCode,
                serviceType,
                email    // ✅ PASS EMAIL
        );
    }
}
