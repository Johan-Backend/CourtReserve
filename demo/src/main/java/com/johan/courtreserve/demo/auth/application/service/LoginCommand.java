package com.johan.courtreserve.demo.auth.application.service;

import java.util.Objects;

public record LoginCommand(String email, String password) {
    public LoginCommand{
        Objects.requireNonNull(email, "The email is required");
        Objects.requireNonNull(password, "The password is required");
    }
}
