package com.johan.courtreserve.demo.auth.domain;

import java.util.UUID;

public record RefreshTokenId(UUID value) {
    public static RefreshTokenId newId() {
        return new RefreshTokenId(UUID.randomUUID());
    }
}