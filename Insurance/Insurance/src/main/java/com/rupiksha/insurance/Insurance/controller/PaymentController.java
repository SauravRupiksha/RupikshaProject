package com.rupiksha.insurance.Insurance.controller;

import com.rupiksha.insurance.Insurance.dto.ApiResponse;
import com.rupiksha.insurance.Insurance.dto.PayRequestDTO;
import com.rupiksha.insurance.Insurance.service.VenusPaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insurance")
public class PaymentController {

    private final VenusPaymentService paymentService;

    public PaymentController(VenusPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<ApiResponse<?>> pay(
            @Valid @RequestBody PayRequestDTO request) {

        ApiResponse<?> response = paymentService.payBill(request);

        return ResponseEntity.ok(response);
    }
}