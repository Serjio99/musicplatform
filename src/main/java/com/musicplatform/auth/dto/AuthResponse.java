package com.musicplatform.auth.dto;

import com.musicplatform.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private UserDto user;
}