package com.musicplatform.payment.controller;

import com.musicplatform.common.dto.ApiResponse;
import com.musicplatform.payment.dto.CreatePaymentRequest;
import com.musicplatform.payment.dto.PaymentDto;
import com.musicplatform.payment.dto.UpdatePaymentStatusRequest;
import com.musicplatform.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/api/payments")
    public ApiResponse<PaymentDto> create(@Valid @RequestBody CreatePaymentRequest request) {
        return ApiResponse.success(paymentService.create(request), "Платёж создан");
    }

    @GetMapping("/api/payments/my")
    public ApiResponse<List<PaymentDto>> myPayments() {
        return ApiResponse.success(paymentService.getMyPayments());
    }

    @GetMapping("/api/admin/payments")
    public ApiResponse<List<PaymentDto>> adminPayments() {
        return ApiResponse.success(paymentService.getAllAdmin());
    }

    @PatchMapping("/api/admin/payments/{id}/status")
    public ApiResponse<PaymentDto> updateStatus(@PathVariable UUID id,
                                                @Valid @RequestBody UpdatePaymentStatusRequest request) {
        return ApiResponse.success(paymentService.updateStatus(id, request), "Статус платежа обновлён");
    }

    @PostMapping("/api/payments/webhook/succeeded/{externalPaymentId}")
    public ApiResponse<PaymentDto> webhookSucceeded(@PathVariable String externalPaymentId) {
        return ApiResponse.success(paymentService.markSucceededByExternalId(externalPaymentId), "Webhook succeeded processed");
    }

    @PostMapping("/api/payments/webhook/canceled/{externalPaymentId}")
    public ApiResponse<PaymentDto> webhookCanceled(@PathVariable String externalPaymentId) {
        return ApiResponse.success(paymentService.markCanceledByExternalId(externalPaymentId), "Webhook canceled processed");
    }
}