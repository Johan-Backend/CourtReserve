package com.johan.courtreserve.demo.auth.domain.exception;

public class RefreshTokenAlreadyRevokedException extends DomainException{
    public RefreshTokenAlreadyRevokedException() {
        super("Refresh token already has revoked");
    }

    public RefreshTokenAlreadyRevokedException(String message, Throwable cause) {
        super(message, cause);
    }
} 