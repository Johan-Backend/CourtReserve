package com.johan.courtreserve.demo.auth.domain.exception;

public class RefreshTokenExpiredException extends DomainException{
    public RefreshTokenExpiredException() {
        super("Refresh token has expired");
    }
    
    public RefreshTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}