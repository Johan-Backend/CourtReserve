package com.johan.courtreserve.demo.user.domain.exception;

public class UserAlreadyExistException extends UserDomainException{
    public UserAlreadyExistException(String email) {
        super("The user Already exist with email: " + email);
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
