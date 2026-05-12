package com.rupiksha.cmsart.controller;

import com.rupiksha.cmsart.dto.Cms5StatusRequest;
import com.rupiksha.cmsart.dto.Cms5StatusResponse;
import com.rupiksha.cmsart.service.Cms5Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cms5")
@RequiredArgsConstructor
public class Cms5StatusController {

    private final Cms5Service cms5Service;

    @PostMapping("/status")
    public Cms5StatusResponse status(
            @RequestBody Cms5StatusRequest request){

        return cms5Service.transactionStatus(request);
    }
}