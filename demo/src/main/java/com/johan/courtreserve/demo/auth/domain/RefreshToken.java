package com.johan.courtreserve.demo.auth.domain;

import java.time.Instant;
import java.util.UUID;

import com.johan.courtreserve.demo.auth.domain.exception.RefreshTokenAlreadyRevokedException;
import com.johan.courtreserve.demo.auth.domain.exception.RefreshTokenExpiredException;
import com.johan.courtreserve.demo.auth.domain.exception.RefreshTokenRevokedException;

public class RefreshToken {
    private final Long id;
    private final UUID familyId;
    private final UUID userPublicId;                 
    private final Instant issuedAt;
    private final Instant expiresAt;
    private boolean revoked;

    private RefreshToken(Long id, UUID familyId, UUID userPublicId, Instant issuedAt, Instant expiresAt) {
        this.id = id;
        this.familyId = familyId;
        this.userPublicId = userPublicId;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public static RefreshToken create(){
        return new RefreshToken(null, UUID.randomUUID(), null, null, null);
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
