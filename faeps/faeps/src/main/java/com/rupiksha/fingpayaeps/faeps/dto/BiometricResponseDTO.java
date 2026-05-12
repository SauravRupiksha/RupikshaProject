package com.rupiksha.fingpayaeps.faeps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BiometricResponseDTO<T> {

    private boolean success;
    private String message;
    private String errorCode;
    private T data;

    // 🔐 Only include when present (debug mode)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String rawResponse;

    private String timestamp;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    // ================= SUCCESS =================

    public static <T> BiometricResponseDTO<T> success(T data, String message) {
        return success(data, message, null);
    }

    public static <T> BiometricResponseDTO<T> success(T data, String message, String rawResponse) {
        return BiometricResponseDTO.<T>builder()
                .success(true)
                .message(message)
                .errorCode("0") // ✅ standard success code
                .data(data)
                .rawResponse(rawResponse)
                .timestamp(currentTime())
                .build();
    }

    // ================= ERROR =================

    public static <T> BiometricResponseDTO<T> error(String message) {
        return error(message, "GENERIC_ERROR", null);
    }

    public static <T> BiometricResponseDTO<T> error(String message, String errorCode) {
        return error(message, errorCode, null);
    }

    public static <T> BiometricResponseDTO<T> error(String message, String errorCode, String rawResponse) {
        return BiometricResponseDTO.<T>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .data(null)
                .rawResponse(rawResponse)
                .timestamp(currentTime())
                .build();
    }

    // ================= TIME =================

    private static String currentTime() {
        return ZonedDateTime.now().format(FORMATTER);
    }
}