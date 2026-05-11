package com.johan.courtreserve.demo.user.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

public record Email(String value){
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    public Email {
        Objects.requireNonNull(value, "The email is required");
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Email is invalid: " + value);
        }
        value = value.toLowerCase().trim();
    }
}