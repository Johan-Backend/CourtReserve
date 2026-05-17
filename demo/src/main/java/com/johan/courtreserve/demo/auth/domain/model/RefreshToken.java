package com.johan.courtreserve.demo.auth.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.johan.courtreserve.demo.auth.domain.exception.RefreshTokenAlreadyRevokedException;
import com.johan.courtreserve.demo.auth.domain.exception.RefreshTokenExpiredException;
import com.johan.courtreserve.demo.auth.domain.exception.RefreshTokenRevokedException;

public class RefreshToken {
    private final Long id;
    private final UUID familyId;
    private final UUID userPublicId;
    private final String tokenHash;
    private final Instant issuedAt;
    private final Instant expiresAt;
    private boolean revoked;

    private RefreshToken(Long id, UUID familyId, UUID userPublicId, String tokenHash,Instant issuedAt, Instant expiresAt) {
        this.id = id;
        this.familyId = familyId;
        this.userPublicId = userPublicId;
        this.tokenHash = tokenHash;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public static RefreshToken create(UUID userPublicId, String tokenHash, Instant expiresAt){
        Objects.requireNonNull(userPublicId, "The public ID is required to create");
        Objects.requireNonNull(expiresAt, "The expiration at is required to create");
        Objects.requireNonNull(tokenHash, "The token hash is required to create");

        return new RefreshToken(null, UUID.randomUUID(), userPublicId, tokenHash, Instant.now(), expiresAt);
    }

    public static RefreshToken reconstitute(Long id, UUID familyId, UUID userPublicId, String tokenHash,Instant issuedAt, Instant expiresAt ){
        Objects.requireNonNull(id, "The ID is required to reconstitute");
        Objects.requireNonNull(familyId, "The family ID is required to reconstitute");
        Objects.requireNonNull(userPublicId, "The public ID is required to reconstitute");
        Objects.requireNonNull(tokenHash, "The token hash is required to reconstitute");
        Objects.requireNonNull(tokenHash, "The issued at is required to reconstitute");
        Objects.requireNonNull(expiresAt, "The expiration at is required to reconstitute");

        return new RefreshToken(id, familyId, userPublicId, tokenHash, issuedAt, expiresAt);
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


    public Long getId() { return this.id; }
    public UUID getFamilyId() { return this.familyId; }
    public UUID getUserPublicId() { return this.userPublicId; }
    public String getTokenHash() { return this.tokenHash; }
    public Instant getIssuedAt() { return this.issuedAt;}
    public Instant getExpiresAt() { return this.expiresAt; }
    public boolean getRevoked() { return this.revoked; }
}