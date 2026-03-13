package com.musicplatform.payment.dto;

import com.musicplatform.payment.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePaymentStatusRequest {

    @NotNull
    private PaymentStatus status;

    private String externalPaymentId;

    private String confirmationUrl;
}