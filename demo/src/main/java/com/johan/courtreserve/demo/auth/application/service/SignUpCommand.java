package com.johan.courtreserve.demo.auth.application.service;

import java.util.Objects;

public record SignUpCommand(
    String firstName, 
    String lastName, 
    String email, 
    String phoneNumber, 
    String password
) {
    public SignUpCommand {
        Objects.requireNonNull(firstName, "The name is required");
        Objects.requireNonNull(lastName, "The lastName is required");
        Objects.requireNonNull(email, "The email is required");
        // phone doesn`t valid: it is optional
        Objects.requireNonNull(password, "The password is required");
    }
}