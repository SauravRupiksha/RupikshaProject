package com.company.fingpay.FingPay.util;



import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class AepsErrorUtil {

    private AepsErrorUtil() {
        // prevent object creation
    }

    private static final Map<String, String> ERROR_MAP;

    static {

        Map<String,String> map = new HashMap<>();

        map.put("00","Transaction Success");
        map.put("FP009","Transaction Pending");
        map.put("FP069","2FA Authentication Required");

        map.put("91","Issuer Timeout");
        map.put("08","Issuer Unavailable");

        map.put("52","No Checking Account");
        map.put("51","Insufficient Balance");

        map.put("59","Suspected Fraud");

        map.put("61","Withdrawal Limit Exceeded");
        map.put("65","Transaction Frequency Exceeded");

        map.put("96","System Malfunction");

        ERROR_MAP = Collections.unmodifiableMap(map);
    }

    public static String getErrorMessage(String code){

        if(code == null){
            return "Unknown Error";
        }

        return ERROR_MAP.getOrDefault(
                code,
                "Unknown Error Code : " + code
        );
    }

}
