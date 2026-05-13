package com.johan.courtreserve.demo.auth.infrastructure.securiity;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    String secret,
    String issuer,
    int accessTokenExpirationMinutes,
    int refreshTokenExpirationDays,
    String refreshCookieName,
    String refreshCookiePath,
    boolean refreshCookieSecure,
    String refreshCookieSameSite
){}