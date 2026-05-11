package com.johan.courtreserve.demo.auth.application.service;

import java.util.Objects;

public record SignUpCommand(
    String name, 
    String lastName, 
    String email, 
    String phone, 
    String password
) {
    public SignUpCommand {
        Objects.requireNonNull(name, "the name is required");
        Objects.requireNonNull(lastName, "lastName es obligatorio");
        Objects.requireNonNull(email, "email es obligatorio");
        // phone doesn`t valid: it is optional
        Objects.requireNonNull(password, "password es obligatorio");
    }
}