package com.rupiksha.fingpayaeps.faeps.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BiometricFrontendRequest {

    @NotBlank(message = "Merchant Login ID is required")
    private String merchantLoginId;

    @NotBlank(message = "Aadhaar is required")
    @Pattern(regexp = "\\d{12}", message = "Aadhaar must be 12 digits")
    private String aadhaarNumber;

    // ✔ String correct
    @NotBlank(message = "Indicator is required")
    @Pattern(regexp = "[02]", message = "indicator must be 0 (Aadhaar) or 2 (VID)")
    private String indicatorforUID;

    // ✔ OPTIONAL (important fix)
    @Pattern(regexp = "\\d{6}", message = "IIN must be 6 digits")
    private String nationalBankIdentificationNumber;

    @NotNull(message = "PrimaryKeyId is required")
    private Integer primaryKeyId;

    @NotBlank(message = "encodeFPTxnId is required")
    private String encodeFPTxnId;

    // 🔥 ADD THIS (VERY IMPORTANT)
    @NotNull(message = "superMerchantId is required")
    private Integer superMerchantId;

    // 🔥 ADD THIS
    private String requestRemarks = "ekyc";

    // 🔥 PID XML (no strict size validation)
    @NotBlank(message = "PID XML is required")
    private String pidXml;
}