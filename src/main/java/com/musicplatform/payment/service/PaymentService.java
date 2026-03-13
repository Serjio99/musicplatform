package com.musicplatform.payment.service;

import com.musicplatform.common.exception.BadRequestException;
import com.musicplatform.common.exception.NotFoundException;
import com.musicplatform.payment.dto.CreatePaymentRequest;
import com.musicplatform.payment.dto.PaymentDto;
import com.musicplatform.payment.dto.UpdatePaymentStatusRequest;
import com.musicplatform.payment.entity.PaymentEntity;
import com.musicplatform.payment.entity.PaymentProvider;
import com.musicplatform.payment.entity.PaymentStatus;
import com.musicplatform.payment.mapper.PaymentMapper;
import com.musicplatform.payment.repository.PaymentRepository;
import com.musicplatform.request.entity.RequestEntity;
import com.musicplatform.request.entity.RequestStatus;
import com.musicplatform.request.repository.RequestRepository;
import com.musicplatform.security.SecurityUtils;
import com.musicplatform.user.entity.UserEntity;
import com.musicplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public PaymentDto create(CreatePaymentRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        UserEntity user = userRepository.findById(currentUserId)
                .filter(entity -> !entity.isDeleted())
                .orElseThrow(() -> new NotFoundException("User not found"));

        RequestEntity requestEntity = requestRepository.findById(request.getRequestId())
                .filter(entity -> !entity.isDeleted())
                .orElseThrow(() -> new NotFoundException("Request not found"));

        if (!requestEntity.getUser().getId().equals(currentUserId)) {
            throw new BadRequestException("Нельзя создать оплату для чужой заявки");
        }

        paymentRepository.findByRequestId(requestEntity.getId()).ifPresent(existing -> {
            throw new BadRequestException("Оплата для этой заявки уже создана");
        });

        PaymentEntity payment = new PaymentEntity();
        payment.setId(UUID.randomUUID());
        payment.setUser(user);
        payment.setRequest(requestEntity);
        payment.setProvider(request.getProvider() == null ? PaymentProvider.YOOKASSA : request.getProvider());
        payment.setStatus(PaymentStatus.WAITING_FOR_CONFIRMATION);
        payment.setAmount(requestEntity.getPrice());
        payment.setCurrency("RUB");
        payment.setDescription(buildDescription(requestEntity));
        payment.setExternalPaymentId("mock_" + UUID.randomUUID());
        payment.setConfirmationUrl("https://pay.example.com/confirm/" + payment.getExternalPaymentId());

        paymentRepository.save(payment);

        requestEntity.setStatus(RequestStatus.PROCESSING);
        requestRepository.save(requestEntity);

        return PaymentMapper.toDto(payment);
    }

    public List<PaymentDto> getMyPayments() {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        return paymentRepository.findAllByUserIdOrderByCreatedAtDesc(currentUserId)
                .stream()
                .map(PaymentMapper::toDto)
                .toList();
    }

    public List<PaymentDto> getAllAdmin() {
        return paymentRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PaymentMapper::toDto)
                .toList();
    }

    public PaymentDto updateStatus(UUID id, UpdatePaymentStatusRequest request) {
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        payment.setStatus(request.getStatus());

        if (request.getExternalPaymentId() != null && !request.getExternalPaymentId().isBlank()) {
            payment.setExternalPaymentId(request.getExternalPaymentId().trim());
        }

        if (request.getConfirmationUrl() != null && !request.getConfirmationUrl().isBlank()) {
            payment.setConfirmationUrl(request.getConfirmationUrl().trim());
        }

        paymentRepository.save(payment);
        syncRequestStatus(payment);

        return PaymentMapper.toDto(payment);
    }

    public PaymentDto markSucceededByExternalId(String externalPaymentId) {
        PaymentEntity payment = paymentRepository.findByExternalPaymentId(externalPaymentId)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        payment.setStatus(PaymentStatus.SUCCEEDED);
        paymentRepository.save(payment);
        syncRequestStatus(payment);

        return PaymentMapper.toDto(payment);
    }

    public PaymentDto markCanceledByExternalId(String externalPaymentId) {
        PaymentEntity payment = paymentRepository.findByExternalPaymentId(externalPaymentId)
                .orElseThrow(() -> new NotFoundException("Payment not found"));

        payment.setStatus(PaymentStatus.CANCELED);
        paymentRepository.save(payment);
        syncRequestStatus(payment);

        return PaymentMapper.toDto(payment);
    }

    private void syncRequestStatus(PaymentEntity payment) {
        RequestEntity request = payment.getRequest();

        switch (payment.getStatus()) {
            case SUCCEEDED -> request.setStatus(RequestStatus.PAID);
            case CANCELED, FAILED -> request.setStatus(RequestStatus.CANCELED);
            case WAITING_FOR_CONFIRMATION, PENDING -> request.setStatus(RequestStatus.PROCESSING);
            default -> { }
        }

        requestRepository.save(request);
    }

    private String buildDescription(RequestEntity request) {
        return "Оплата заявки " + request.getId() + " для трека \"" + request.getSong().getName() + "\"";
    }
}