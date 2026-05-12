package com.rupiksha.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CmsCallbackResponse {

    private String status_id;
    private String message;
    private String client_ref_id;

}