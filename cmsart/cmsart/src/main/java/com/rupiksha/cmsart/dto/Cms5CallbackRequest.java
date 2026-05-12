package com.rupiksha.cmsart.dto;

import lombok.Data;

@Data
public class Cms5CallbackRequest {

    private String status_id;
    private String txnid;
    private String amount;
    private String message;
    private String user_id;
    private String operator_ref_id;
    private String type;
    private String api_agent_id;
    private String timestamp;
    private String client_ref_id;
    private String provider_name;
    private String provider_id;
    private String ca_no;
    private String description;

}