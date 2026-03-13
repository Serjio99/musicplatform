package com.musicplatform.auth.controller;

import com.musicplatform.auth.dto.*;
import com.musicplatform.auth.service.AuthService;
import com.musicplatform.common.dto.ApiResponse;
import com.musicplatform.user.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.success(authService.register(request), "Регистрация выполнена успешно");
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request), "Авторизация выполнена успешно");
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ApiResponse.success(authService.refresh(request), "Токен успешно обновлён");
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authService.logout(request);
        return ApiResponse.success(null, "Выход выполнен успешно");
    }

    @GetMapping("/me")
    public ApiResponse<UserDto> me() {
        return ApiResponse.success(authService.getCurrentUser());
    }
}