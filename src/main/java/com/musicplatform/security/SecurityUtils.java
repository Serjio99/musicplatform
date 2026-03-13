package com.musicplatform.security;

import com.musicplatform.common.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getDetails() == null) {
            throw new UnauthorizedException("Пользователь не авторизован");
        }

        Object details = authentication.getDetails();

        if (details instanceof UUID userId) {
            return userId;
        }

        throw new UnauthorizedException("Не удалось определить текущего пользователя");
    }

    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new UnauthorizedException("Пользователь не авторизован");
        }

        return authentication.getPrincipal().toString();
    }
}