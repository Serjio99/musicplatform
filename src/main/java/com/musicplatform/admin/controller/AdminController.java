package com.musicplatform.admin.controller;

import com.musicplatform.admin.dto.AdminDashboardDto;
import com.musicplatform.admin.service.AdminService;
import com.musicplatform.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/admin/dashboard")
    public ApiResponse<AdminDashboardDto> dashboard() {
        return ApiResponse.success(adminService.getDashboard());
    }
}