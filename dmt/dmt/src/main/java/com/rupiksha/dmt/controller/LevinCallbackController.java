package com.rupiksha.dmt.controller;

import com.rupiksha.dmt.dto.BankDownCallbackRequest;
import com.rupiksha.dmt.service.BankStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/levin")
public class LevinCallbackController {

    @Autowired
    private BankStatusService service;

    @PostMapping("/bank-down-callback")
    public String bankDownCallback(
            @RequestBody BankDownCallbackRequest request){

        service.saveBankStatus(request);

        return "SUCCESS";
    }
}