package com.rupiksha.recharge.recharge.controller;

import com.rupiksha.recharge.recharge.dto.ApiResponse;
import com.rupiksha.recharge.recharge.dto.RechargeRequest;
import com.rupiksha.recharge.recharge.dto.RechargeResponse;
import com.rupiksha.recharge.recharge.service.RechargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recharge")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RechargeController {

    private final RechargeService rechargeService;

    @PostMapping
    public ApiResponse<?> recharge(
            @RequestBody RechargeRequest request
    ) {

        try {

            RechargeResponse response =
                    rechargeService.doRecharge(request);

            return new ApiResponse<>(
                    true,
                    response.getDescription(),
                    response
            );

        } catch (Exception e) {

            return new ApiResponse<>(
                    false,
                    e.getMessage(),
                    null
            );
        }
    }
}