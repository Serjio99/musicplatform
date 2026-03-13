package com.musicplatform.payment.mapper;

import com.musicplatform.payment.dto.PaymentDto;
import com.musicplatform.payment.entity.PaymentEntity;

public class PaymentMapper {

    public static PaymentDto toDto(PaymentEntity entity) {
        return PaymentDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .userName(entity.getUser().getName())
                .requestId(entity.getRequest().getId())
                .songId(entity.getRequest().getSong().getId())
                .songName(entity.getRequest().getSong().getName())
                .provider(entity.getProvider())
                .status(entity.getStatus())
                .amount(entity.getAmount())
                .currency(entity.getCurrency())
                .externalPaymentId(entity.getExternalPaymentId())
                .confirmationUrl(entity.getConfirmationUrl())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}