package com.rupiksha.cmsart.controller;


import com.rupiksha.cmsart.dto.Cms5TransactionRequest;
import com.rupiksha.cmsart.service.Cms5Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cms5")
@RequiredArgsConstructor
public class Cms5Controller {

    private final Cms5Service cms5Service;

    @PostMapping("/transaction")
    public ResponseEntity<?> transaction(
            @RequestBody Cms5TransactionRequest request) {

        return ResponseEntity.ok(
                cms5Service.transaction(request)
        );
    }
}