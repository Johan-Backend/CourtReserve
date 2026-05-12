package com.johan.courtreserve.demo.auth.domain.exception;

public class InvalidCredentials extends AuthDomainException {
    public InvalidCredentials() {
        super("The email or password is incorrect");
    }

    public InvalidCredentials(String message, Throwable cause) {
        super(message, cause);
    }
}