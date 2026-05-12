package com.rupiksha.levin.utility.controller;

import com.rupiksha.levin.utility.dto.*;
import com.rupiksha.levin.utility.service.LevinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/levin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LevinController {

    private final LevinService levinService;

    // =========================================================
    // MOBILE / DTH RECHARGE API
    // =========================================================

    @PostMapping("/recharge")
    public ResponseEntity<LevinRechargeResponse> recharge(
            @RequestBody RechargeRequest request
    ) {

        LevinRechargeResponse response =
                levinService.doRecharge(request);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    // BBPS FETCH BILL API
    // =========================================================

    @PostMapping("/bbps/fetch-bill")
    public ResponseEntity<BbpsFetchBillResponse> fetchBill(
            @RequestBody BbpsFetchBillRequest request
    ) {

        BbpsFetchBillResponse response =
                levinService.fetchBill(request);

        return ResponseEntity.ok(response);
    }
    // =========================================================
    // BBPS PAY BILL API
    // =========================================================

    @PostMapping("/bbps/pay-bill")
    public ResponseEntity<BbpsPayBillResponse> payBill(
            @RequestBody BbpsPayBillRequest request
    ) {

        BbpsPayBillResponse response =
                levinService.payBill(request);

        return ResponseEntity.ok(response);
    }
}