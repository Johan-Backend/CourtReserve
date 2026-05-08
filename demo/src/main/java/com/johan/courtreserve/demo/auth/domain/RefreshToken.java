package com.johan.courtreserve.demo.auth.domain;

import java.time.Instant;
import java.util.UUID;

import com.johan.courtreserve.demo.auth.domain.exception.RefreshTokenAlreadyRevokedException;
import com.johan.courtreserve.demo.auth.domain.exception.RefreshTokenExpiredException;
import com.johan.courtreserve.demo.auth.domain.exception.RefreshTokenRevokedException;

public class RefreshToken {
    private final RefreshTokenId id;
    private final UUID userId;                 
    private final Instant issuedAt;
    private final Instant expiresAt;
    private boolean revoked;

    public RefreshToken(RefreshTokenId id, UUID userId, Instant issuedAt, Instant expiresAt, boolean revoked) {
        if (id == null) throw new IllegalArgumentException("id required");
        if (userId == null) throw new IllegalArgumentException("userId required");
        if (issuedAt == null) throw new IllegalArgumentException("issuedAt required");
        if (expiresAt == null) throw new IllegalArgumentException("expiresAt required");
        if (!expiresAt.isAfter(issuedAt)) {
            throw new IllegalArgumentException("expiresAt must be after issuedAt");
        }
        this.id = id;
        this.userId = userId;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
    }

    public void revoke() {
        if (this.revoked) {
            throw new RefreshTokenAlreadyRevokedException();
        }
        this.revoked = true;
    }
    
    public void ensureUsable(Instant now) {
        if (revoked) throw new RefreshTokenRevokedException();
        if (now.isAfter(expiresAt)) throw new RefreshTokenExpiredException();
    }
}
