package com.rupiksha.cms.controller;

import com.rupiksha.cms.dto.C3TransactionRequest;
import com.rupiksha.cms.dto.C3TransactionResponse;
import com.rupiksha.cms.service.LevinC3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/levin/c3")
@CrossOrigin(origins = "*")
public class LevinC3Controller {

    private final LevinC3Service service;

    public LevinC3Controller(LevinC3Service service) {
        this.service = service;
    }

    @PostMapping("/transaction")
    public ResponseEntity<C3TransactionResponse> transaction(
            @RequestBody C3TransactionRequest request){

        C3TransactionResponse response = service.transaction(request);

        return ResponseEntity.ok(response);
    }

}