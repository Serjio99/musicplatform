package com.musicplatform.auth.service;

import com.musicplatform.auth.dto.*;
import com.musicplatform.auth.entity.RefreshTokenEntity;
import com.musicplatform.auth.repository.RefreshTokenRepository;
import com.musicplatform.common.enums.UserRole;
import com.musicplatform.common.exception.BadRequestException;
import com.musicplatform.common.exception.NotFoundException;
import com.musicplatform.common.exception.UnauthorizedException;
import com.musicplatform.security.JwtService;
import com.musicplatform.security.SecurityUtils;
import com.musicplatform.user.dto.UserDto;
import com.musicplatform.user.entity.UserEntity;
import com.musicplatform.user.mapper.UserMapper;
import com.musicplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmailAndDeletedFalse(request.getEmail())) {
            throw new BadRequestException("Пользователь с таким email уже существует");
        }

        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName().trim());
        user.setRole(UserRole.USER);

        userRepository.save(user);

        return buildAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().trim().toLowerCase(),
                        request.getPassword()
                )
        );

        UserEntity user = userRepository.findByEmailAndDeletedFalse(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new NotFoundException("User not found"));

        return buildAuthResponse(user);
    }

    public AuthResponse refresh(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtService.isTokenValid(refreshToken) || !"refresh".equals(jwtService.extractTokenType(refreshToken))) {
            throw new UnauthorizedException("Недействительный refresh token");
        }

        RefreshTokenEntity storedToken = refreshTokenRepository.findByTokenAndRevokedFalse(refreshToken)
                .orElseThrow(() -> new UnauthorizedException("Refresh token not found"));

        if (storedToken.getExpiresAt().isBefore(OffsetDateTime.now(ZoneOffset.UTC))) {
            throw new UnauthorizedException("Refresh token expired");
        }

        UserEntity user = storedToken.getUser();

        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);

        return buildAuthResponse(user);
    }

    public void logout(RefreshTokenRequest request) {
        refreshTokenRepository.findByTokenAndRevokedFalse(request.getRefreshToken())
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });
    }

    public UserDto getCurrentUser() {
        String email = SecurityUtils.getCurrentUserEmail();

        UserEntity user = userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return UserMapper.toDto(user);
    }

    private AuthResponse buildAuthResponse(UserEntity user) {
        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail(), user.getRole().name());
        String refreshToken = jwtService.generateRefreshToken(user.getId());

        refreshTokenRepository.deleteAllByUser(user);

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setId(UUID.randomUUID());
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpiresAt(OffsetDateTime.now(ZoneOffset.UTC)
                .plusSeconds(jwtService.getRefreshTokenExpirationSeconds()));

        refreshTokenRepository.save(refreshTokenEntity);

        UserDto userDto = UserMapper.toDto(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .user(userDto)
                .build();
    }
}
