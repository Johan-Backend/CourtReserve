package com.johan.courtreserve.demo.auth.infrastructure.securiity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.johan.courtreserve.demo.auth.application.port.TokenGenerator;
import com.johan.courtreserve.demo.user.domain.model.User;

import lombok.RequiredArgsConstructor;

// Ouput Adapter
@Service
@RequiredArgsConstructor
public class JwtServiceAdapater implements TokenGenerator{
    private final JwtEncoder encoder;
    private final JwtProperties properties;

    public String generateAccessToken(User user){
        Instant now = Instant.now();
        Instant expiresAt  = now.plus(properties.accessTokenExpirationMinutes(), ChronoUnit.MINUTES);
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer(properties.issuer())
            .subject(user.getPublicId().toString())
            .issuedAt(now)
            .expiresAt(expiresAt)
            .claim("role", expiresAt)
            .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}