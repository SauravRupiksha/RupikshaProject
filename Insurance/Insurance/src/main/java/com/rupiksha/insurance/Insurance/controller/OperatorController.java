package com.rupiksha.insurance.Insurance.controller;

import com.rupiksha.insurance.Insurance.dto.ApiResponse;
import com.rupiksha.insurance.Insurance.entity.Operator;
import com.rupiksha.insurance.Insurance.repository.OperatorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/operators")
public class OperatorController {

    private final OperatorRepository repository;

    public OperatorController(OperatorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getOperators(
            @RequestParam(defaultValue = "INS") String serviceType) {

        List<Operator> operators = repository.findByServiceType(serviceType);

        // ✅ Convert to clean response
        List<?> response = operators.stream()
                .map(op -> new OperatorResponse(
                        op.getName(),
                        op.getCode()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>("success", "Operators fetched", response)
        );
    }

    // 🔥 Inner DTO (clean response)
    static class OperatorResponse {
        public String name;
        public String code;

        public OperatorResponse(String name, String code) {
            this.name = name;
            this.code = code;
        }
    }
}