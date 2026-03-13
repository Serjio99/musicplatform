package com.musicplatform.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.security")
public class JwtProperties {

    private String jwtSecret;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;
}