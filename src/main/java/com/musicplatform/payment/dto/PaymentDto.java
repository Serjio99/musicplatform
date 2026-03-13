package com.musicplatform.payment.dto;

import com.musicplatform.payment.entity.PaymentProvider;
import com.musicplatform.payment.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
public class PaymentDto {

    private UUID id;
    private UUID userId;
    private String userName;
    private UUID requestId;
    private UUID songId;
    private String songName;
    private PaymentProvider provider;
    private PaymentStatus status;
    private BigDecimal amount;
    private String currency;
    private String externalPaymentId;
    private String confirmationUrl;
    private String description;
    private OffsetDateTime createdAt;
}