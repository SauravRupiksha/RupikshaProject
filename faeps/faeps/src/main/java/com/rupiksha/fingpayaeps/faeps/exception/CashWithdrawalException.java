package com.rupiksha.fingpayaeps.faeps.exception;

public class CashWithdrawalException extends RuntimeException {

    private final String errorCode;
    private final String merchantTranId;

    public CashWithdrawalException(String message, String errorCode, String merchantTranId) {
        super(message);
        this.errorCode = errorCode;
        this.merchantTranId = merchantTranId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMerchantTranId() {
        return merchantTranId;
    }
}