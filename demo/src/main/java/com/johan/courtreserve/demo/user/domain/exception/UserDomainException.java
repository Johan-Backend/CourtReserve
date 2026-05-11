package com.johan.courtreserve.demo.user.domain.exception;

public abstract class UserDomainException extends RuntimeException{
    protected UserDomainException(String message) {
        super(message);
    }
    
    protected UserDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}