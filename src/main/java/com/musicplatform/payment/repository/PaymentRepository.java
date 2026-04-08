package com.musicplatform.payment.repository;

import com.musicplatform.payment.entity.PaymentEntity;
import com.musicplatform.payment.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    List<PaymentEntity> findAllByUserIdOrderByCreatedAtDesc(UUID userId);

    List<PaymentEntity> findAllByOrderByCreatedAtDesc();

    Optional<PaymentEntity> findByRequestId(UUID requestId);

    Optional<PaymentEntity> findByExternalPaymentId(String externalPaymentId);

    long countByStatus(PaymentStatus status);
}
