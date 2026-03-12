package com.company.fingpay.FingPay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerUtil {

    // Global logger (optional)
    public static final Logger logger =
            LoggerFactory.getLogger("AEPS");

    private LoggerUtil() {
        // prevent object creation
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

}