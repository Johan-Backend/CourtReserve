package com.johan.courtreserve.demo.auth.domain;

import java.time.Duration;

public record TokenLifetime(Duration value) {
    public TokenLifetime {
        if (value.isNegative() || value.isZero()) {
            throw new IllegalArgumentException("lifetime must be positive");
        }
    }
}