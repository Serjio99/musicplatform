package com.musicplatform.payment.entity;

public enum PaymentStatus {
    PENDING,
    WAITING_FOR_CONFIRMATION,
    SUCCEEDED,
    CANCELED,
    FAILED
}