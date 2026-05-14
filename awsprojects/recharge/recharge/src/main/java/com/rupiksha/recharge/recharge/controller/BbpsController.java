package com.rupiksha.recharge.recharge.controller;

import com.rupiksha.recharge.recharge.dto.FetchBillRequest;
import com.rupiksha.recharge.recharge.dto.FetchBillResponse;

import com.rupiksha.recharge.recharge.dto.PayBillRequest;
import com.rupiksha.recharge.recharge.dto.PayBillResponse;

import com.rupiksha.recharge.recharge.service.BbpsService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bbps")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BbpsController {

    private final BbpsService bbpsService;

    // ==============================
    // FETCH BILL
    // ==============================

    @PostMapping("/fetch-bill")
    public FetchBillResponse fetchBill(
            @RequestBody
            FetchBillRequest request
    ) {

        return bbpsService.fetchBill(
                request
        );
    }

    // ==============================
    // PAY BILL
    // ==============================

    @PostMapping("/pay-bill")
    public PayBillResponse payBill(
            @RequestBody
            PayBillRequest request
    ) {

        return bbpsService.payBill(
                request
        );
    }
}