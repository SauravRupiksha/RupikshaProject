package com.rupiksha.insurance.Insurance.entity;

public enum Status {

    // 🔹 Initial
    INITIATED,

    // 🔹 Fetch flow
    FETCH_INITIATED,
    FETCH_SUCCESS,
    FETCH_FAILED,

    // 🔹 Payment flow
    PAYMENT_INITIATED,
    PENDING,            // waiting for callback
    SUCCESS,
    FAILED,

    // 🔹 Refund flow (optional for future)
    REFUND
}