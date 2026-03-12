package com.company.fingpay.FingPay.exception;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* -------------------------
        GENERAL EXCEPTION
    -------------------------- */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                "SYSTEM_ERROR",
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    /* -------------------------
        VALIDATION EXCEPTION
    -------------------------- */

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                "VALIDATION_ERROR",
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }


    /* -------------------------
        ILLEGAL ARGUMENT
    -------------------------- */

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                "INVALID_REQUEST",
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }

}
