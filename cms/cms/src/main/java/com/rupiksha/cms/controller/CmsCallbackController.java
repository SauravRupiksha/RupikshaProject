package com.rupiksha.cms.controller;

import com.rupiksha.cms.dto.CmsCallbackRequest;
import com.rupiksha.cms.dto.CmsCallbackResponse;
import com.rupiksha.cms.service.CmsCallbackService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/levin/cms")
public class CmsCallbackController {

    private final CmsCallbackService service;

    public CmsCallbackController(
            CmsCallbackService service) {
        this.service = service;
    }

    @PostMapping("/callback")
    public CmsCallbackResponse callback(
            @RequestBody CmsCallbackRequest request){

        return service.processCallback(request);
    }

}