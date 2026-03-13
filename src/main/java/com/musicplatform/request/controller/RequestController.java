package com.musicplatform.request.controller;

import com.musicplatform.common.dto.ApiResponse;
import com.musicplatform.request.dto.CreateRequestRequest;
import com.musicplatform.request.dto.RequestDto;
import com.musicplatform.request.dto.UpdateRequestStatusRequest;
import com.musicplatform.request.service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/api/requests")
    public ApiResponse<RequestDto> create(@Valid @RequestBody CreateRequestRequest request) {
        return ApiResponse.success(requestService.create(request), "Заявка создана");
    }

    @GetMapping("/api/requests/my")
    public ApiResponse<List<RequestDto>> myRequests() {
        return ApiResponse.success(requestService.getMyRequests());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/admin/requests")
    public ApiResponse<List<RequestDto>> adminRequests() {
        return ApiResponse.success(requestService.getAllAdmin());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/api/admin/requests/{id}/status")
    public ApiResponse<RequestDto> updateStatus(@PathVariable UUID id,
                                                @Valid @RequestBody UpdateRequestStatusRequest request) {
        return ApiResponse.success(requestService.updateStatus(id, request));
    }
}