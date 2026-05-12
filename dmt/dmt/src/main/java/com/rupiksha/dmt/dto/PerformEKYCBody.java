package com.rupiksha.dmt.dto;

import lombok.Data;

@Data
public class PerformEKYCBody {

    private String mobile;
    private String token;
    private String pidata;
}