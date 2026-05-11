package com.johan.courtreserve.demo.user.domain.model;

import java.util.Objects;
import java.util.UUID;

public class User {
    private final Long id;      
    private final UUID publicId;            
    private String name;
    private String lastName;
    private Email email;
    private String phone;
    private String hashedPassword;

    private User(Long id, UUID publicId, String name, String lastName, Email email, String phone, String hashedPassword) {
        this.id = id;
        this.publicId = publicId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.hashedPassword = hashedPassword;
    }

    public static User create(String name, String lastName, Email email, String phone, String hashedPassword) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(email);
        Objects.requireNonNull(hashedPassword);
        
        return new User(null, UUID.randomUUID(), name, lastName, email, phone, hashedPassword);
    }


    public void changeEmail(Email newEmail) {
        Objects.requireNonNull(newEmail);
        this.email = newEmail;
    }
    
    public void changePassword(String newHashedPassword) {
        this.hashedPassword = newHashedPassword;
    }

    public void updateProfile(String name, String lastName, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
    }


    public Long getId() { return id; }
    public String getFirstName() { return name; }
    public String getLastName() { return lastName; }
    public Email getEmail() { return email; }
    public String getPhoneNumber() { return phone; }
    public String getHashedPassword() { return hashedPassword; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return publicId.equals(other.publicId);
    }
    
    @Override
    public int hashCode() {
        return publicId.hashCode();
    }
}