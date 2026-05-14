package com.johan.courtreserve.demo.auth.domain.port.out;

import java.util.Optional;
import java.util.UUID;

import com.johan.courtreserve.demo.auth.domain.RefreshToken;

public interface RefreshTokenRepository {
    void save(RefreshToken token);
    Optional<RefreshToken> findById(Long id);
    void revokeAllForUser(UUID userId);
}