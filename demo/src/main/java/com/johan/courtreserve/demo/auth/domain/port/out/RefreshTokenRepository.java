package com.johan.courtreserve.demo.auth.domain.port.out;

import java.util.Optional;
import java.util.UUID;

import com.johan.courtreserve.demo.auth.domain.RefreshToken;
import com.johan.courtreserve.demo.auth.domain.RefreshTokenId;

public interface RefreshTokenRepository {
    void save(RefreshToken token);
    Optional<RefreshToken> findById(RefreshTokenId id);
    void revokeAllForUser(UUID userId);
}