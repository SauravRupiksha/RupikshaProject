package com.rupiksha.fingpayaeps.faeps.controller;

import com.rupiksha.fingpayaeps.faeps.dto.CashWithdrawalRequest;
import com.rupiksha.fingpayaeps.faeps.dto.CashWithdrawalResponse;

import com.rupiksha.fingpayaeps.faeps.service.CashWithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aeps/cash")
@RequiredArgsConstructor
public class CashWithdrawalController {

    private final CashWithdrawalService service;

    @PostMapping("/withdraw")
    public ResponseEntity<CashWithdrawalResponse> withdraw(
            @RequestBody CashWithdrawalRequest request) {

        CashWithdrawalResponse response = service.withdraw(request);
        return ResponseEntity.ok(response);
    }
}