package com.johan.courtreserve.demo.auth.domain.exception;

public class RefreshTokenRevokedException extends AuthDomainException{
    public RefreshTokenRevokedException() {
        super("Refresh token has revoked");
    }

    public RefreshTokenRevokedException(String message, Throwable cause) {
        super(message, cause);
    }
}