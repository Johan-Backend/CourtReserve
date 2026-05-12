package com.johan.courtreserve.demo.auth.domain.exception;

public abstract class AuthDomainException extends RuntimeException{
    protected AuthDomainException(String message) {
        super(message);
    }
    
    protected AuthDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}