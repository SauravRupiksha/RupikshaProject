package com.rupiksha.insurance.Insurance.controller;

import com.rupiksha.insurance.Insurance.dto.ApiResponse;
import com.rupiksha.insurance.Insurance.dto.FetchRequestDTO;
import com.rupiksha.insurance.Insurance.service.VenusFetchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

    private final VenusFetchService fetchService;

    public InsuranceController(VenusFetchService fetchService) {
        this.fetchService = fetchService;
    }

    @PostMapping("/fetch")
    public ResponseEntity<ApiResponse<?>> fetchBill(
            @Valid @RequestBody FetchRequestDTO request) {

        ApiResponse<?> response = fetchService.fetchBill(request);

        return ResponseEntity.ok(response);
    }
}