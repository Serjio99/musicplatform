package com.musicplatform.payment.dto;

import com.musicplatform.payment.entity.PaymentProvider;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePaymentRequest {

    @NotNull
    private UUID requestId;

    private PaymentProvider provider = PaymentProvider.YOOKASSA;
}