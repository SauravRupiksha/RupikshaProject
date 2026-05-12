package com.rupiksha.fingpayaeps.faeps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EkycStatusResponseDTO {

    private boolean success;        // internal success
    private String message;

    private String kycStatus;       // ✅ renamed (COMPLETED/PENDING/FAILED)
    private Integer statusCode;

    private Object data;

    private String errorCode;
    private String rawResponse;

    private String timestamp;

    // ✅ SUCCESS
    public static EkycStatusResponseDTO success(String message,
                                                String kycStatus,
                                                Integer statusCode,
                                                Object data,
                                                String rawResponse) {

        return EkycStatusResponseDTO.builder()
                .success(true)
                .message(message)
                .kycStatus(kycStatus)
                .statusCode(statusCode)
                .data(data)
                .rawResponse(rawResponse)
                .timestamp(now())
                .build();
    }

    // ❌ ERROR
    public static EkycStatusResponseDTO error(String message,
                                              String errorCode,
                                              String rawResponse) {

        return EkycStatusResponseDTO.builder()
                .success(false)
                .message(message)
                .kycStatus("FAILED")
                .errorCode(errorCode)
                .rawResponse(rawResponse)
                .timestamp(now())
                .build();
    }

    private static String now() {
        return LocalDateTime.now().toString();
    }
}