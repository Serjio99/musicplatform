package com.musicplatform.security;

import com.musicplatform.user.entity.UserEntity;
import com.musicplatform.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token) || !"access".equals(jwtService.extractTokenType(token))) {
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UUID userId = jwtService.extractUserId(token);
            UserEntity user = userRepository.findById(userId).orElse(null);

            if (user != null && !user.isDeleted()) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                );
                authentication.setDetails(user.getId());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
