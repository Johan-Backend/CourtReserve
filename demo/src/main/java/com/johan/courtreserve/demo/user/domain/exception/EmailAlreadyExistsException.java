package com.johan.courtreserve.demo.user.domain.exception;

public class EmailAlreadyExistsException extends UserDomainException{
    public EmailAlreadyExistsException() {
        super("The email Already exist.");
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
