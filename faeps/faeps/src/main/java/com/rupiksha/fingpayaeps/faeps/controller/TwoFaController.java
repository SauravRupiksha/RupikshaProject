package com.rupiksha.fingpayaeps.faeps.controller;

import com.rupiksha.fingpayaeps.faeps.dto.TwoFaRequest;
import com.rupiksha.fingpayaeps.faeps.dto.TwoFaResponse;
import com.rupiksha.fingpayaeps.faeps.service.TwoFaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/aeps/2fa")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class TwoFaController {

    private final TwoFaService service;

    @PostMapping("/auth")
    public ResponseEntity<TwoFaResponse> do2FA(
            @Valid @RequestBody TwoFaRequest request
    ) {

        try {
            log.info("➡️ 2FA Request Received: txnId={}", request.getMerchantTranId());

            // 🔥 SAFE DEBUG (NO SENSITIVE DATA)
            if (request.getCaptureResponse() != null) {

                String pidData = request.getCaptureResponse().getPiddata();

                log.info("📦 PIDDATA LENGTH => {}",
                        pidData != null ? pidData.length() : 0);

                log.info("📦 DEVICE => dpID={}, rdsID={}",
                        request.getCaptureResponse().getDpID(),
                        request.getCaptureResponse().getRdsID());
            }

            // ================= CALL SERVICE =================
            TwoFaResponse response = service.do2FA(request);

            log.info("⬅️ 2FA Response: status={}, message={}, code={}",
                    response.isStatus(),
                    response.getMessage(),
                    response.getStatusCode());

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            log.error("❌ 2FA Error: {}", e.getMessage(), e);

            TwoFaResponse error = new TwoFaResponse();
            error.setStatus(false);
            error.setMessage("2FA Failed: " + e.getMessage());
            error.setStatusCode(500L);

            return ResponseEntity.internalServerError().body(error);
        }
    }
}