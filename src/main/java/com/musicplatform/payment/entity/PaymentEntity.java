package com.musicplatform.payment.entity;

import com.musicplatform.common.entity.BaseEntity;
import com.musicplatform.request.entity.RequestEntity;
import com.musicplatform.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class PaymentEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_id", nullable = false, unique = true)
    private RequestEntity request;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private PaymentProvider provider = PaymentProvider.YOOKASSA;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 10)
    private String currency = "RUB";

    @Column(name = "external_payment_id", length = 255)
    private String externalPaymentId;

    @Column(name = "confirmation_url", length = 2000)
    private String confirmationUrl;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}