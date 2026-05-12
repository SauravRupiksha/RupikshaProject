package com.rupiksha.fingpayaeps.faeps.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class AadhaarValidator {

    private static final Logger log = LoggerFactory.getLogger(AadhaarValidator.class);

    // 🔥 Precompiled regex
    private static final Pattern VALID_AADHAAR = Pattern.compile("\\d{12}");
    private static final Pattern SAME_DIGITS = Pattern.compile("(\\d)\\1{11}");

    // 🔐 Verhoeff tables
    private static final int[][] d = {
            {0,1,2,3,4,5,6,7,8,9},
            {1,2,3,4,0,6,7,8,9,5},
            {2,3,4,0,1,7,8,9,5,6},
            {3,4,0,1,2,8,9,5,6,7},
            {4,0,1,2,3,9,5,6,7,8},
            {5,9,8,7,6,0,4,3,2,1},
            {6,5,9,8,7,1,0,4,3,2},
            {7,6,5,9,8,2,1,0,4,3},
            {8,7,6,5,9,3,2,1,0,4},
            {9,8,7,6,5,4,3,2,1,0}
    };

    private static final int[][] p = {
            {0,1,2,3,4,5,6,7,8,9},
            {1,5,7,6,2,8,3,0,9,4},
            {5,8,0,3,7,9,6,1,4,2},
            {8,9,1,6,0,4,3,5,2,7},
            {9,4,5,3,1,2,6,8,7,0},
            {4,2,8,6,5,7,3,9,0,1},
            {2,7,9,3,8,0,6,4,1,5},
            {7,0,4,6,9,1,3,2,5,8}
    };

    private AadhaarValidator() {}

    /**
     * ✅ AEPS Safe Validation (Recommended)
     */
    public static boolean isValid(String aadhaar) {

        if (aadhaar == null || aadhaar.isBlank()) {
            log.warn("Aadhaar validation failed: empty input");
            return false;
        }

        // 🔴 Strict format (NO cleaning)
        if (!VALID_AADHAAR.matcher(aadhaar).matches()) {
            log.warn("Aadhaar validation failed: invalid format {}", mask(aadhaar));
            return false;
        }

        // 🔴 Optional: repeated digits block
        if (SAME_DIGITS.matcher(aadhaar).matches()) {
            log.warn("Aadhaar validation failed: repeated digits {}", mask(aadhaar));
            return false;
        }

        return true;
    }

    /**
     * 🔐 Optional strict validation (NOT recommended for AEPS)
     */
    public static boolean isValidStrict(String aadhaar) {
        return isValid(aadhaar) && validateVerhoeff(aadhaar);
    }

    // 🔐 Verhoeff checksum
    private static boolean validateVerhoeff(String num) {
        int c = 0;

        for (int i = 0; i < num.length(); i++) {
            int digit = num.charAt(num.length() - i - 1) - '0';
            c = d[c][p[i % 8][digit]];
        }

        return c == 0;
    }

    // 🔐 Mask Aadhaar
    public static String mask(String aadhaar) {
        if (aadhaar == null || aadhaar.length() < 4) return "****";
        return aadhaar.replaceAll("\\d(?=\\d{4})", "*");
    }
}