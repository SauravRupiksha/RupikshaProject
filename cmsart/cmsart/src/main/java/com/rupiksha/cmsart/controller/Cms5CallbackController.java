package com.rupiksha.cmsart.controller;

import com.rupiksha.cmsart.dto.Cms5CallbackRequest;
import com.rupiksha.cmsart.dto.Cms5CallbackResponse;
import com.rupiksha.cmsart.service.Cms5Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cms5")
@RequiredArgsConstructor
public class Cms5CallbackController {

    private final Cms5Service cms5Service;

    @PostMapping("/callback")
    public Cms5CallbackResponse callback(
            @RequestBody Cms5CallbackRequest request){

        return cms5Service.handleCallback(request);
    }
}