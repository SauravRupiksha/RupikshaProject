package com.rupiksha.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class C3TransactionResponse {

    private Integer status_id;
    private String message;
    private String redirect_url;

}