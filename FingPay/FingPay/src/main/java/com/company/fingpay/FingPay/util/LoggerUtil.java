package com.company.fingpay.FingPay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerUtil {

    private LoggerUtil() {}

    /* -------------------------------
        CLASS BASED LOGGER
    -------------------------------- */

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /* -------------------------------
        GLOBAL AEPS LOGGER
    -------------------------------- */

    public static Logger getAepsLogger() {
        return LoggerFactory.getLogger("AEPS");
    }
}