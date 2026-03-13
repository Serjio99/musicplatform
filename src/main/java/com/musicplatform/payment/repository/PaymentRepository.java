
package com.musicplatform.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.musicplatform.payment.entity.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
}
