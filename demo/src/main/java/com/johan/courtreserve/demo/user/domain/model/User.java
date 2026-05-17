package com.johan.courtreserve.demo.user.domain.model;

import java.util.Objects;
import java.util.UUID;

public class User {
    private final Long id;      
    private final UUID publicId;            
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String hashedPassword;
    private Role role;

    private User(Long id, UUID publicId, String firstName, String lastName, String email, String phone, String hashedPassword, Role role) {
        this.id = id;
        this.publicId = publicId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    public static User create(String firstName, String lastName, String email, String phone, String hashedPassword) {
        Objects.requireNonNull(firstName, "The first name is required to create");
        Objects.requireNonNull(lastName, "The last name is required to create");
        Objects.requireNonNull(email, "The email is required to create");
        Objects.requireNonNull(hashedPassword, "The hashed password is required to create");
        
        return new User(null, UUID.randomUUID(), firstName, lastName, email, phone, hashedPassword, Role.USER);
    }

    public static User reconstitute(Long id, UUID publicId, String firstName, String lastName, String email, String phoneNumber, String hashedPassword, Role role) {
        Objects.requireNonNull(id, "The id is required to reconstitute");
        Objects.requireNonNull(publicId, "the public Id is required to reconstitute");
        Objects.requireNonNull(firstName, "The first name is required to reconstitute");
        Objects.requireNonNull(lastName, "The last name is required to reconstitute");
        Objects.requireNonNull(email, "The enail is required to reconstitute");
        Objects.requireNonNull(hashedPassword, "The hashed password is required to reconstitute");
        Objects.requireNonNull(role, "The role is required to reconstitute");
         
        return new User(id, publicId, firstName, lastName, email, phoneNumber, hashedPassword, role);
    }

    public void changeEmail(String newEmail) {
        Objects.requireNonNull(newEmail);
        this.email = newEmail;
    }
    
    public void changePassword(String newHashedPassword) {
        this.hashedPassword = newHashedPassword;
    }

    public void updateProfile(String name, String lastName, String phone) {
        this.firstName = name;
        this.lastName = lastName;
        this.phone = phone;
    }

    public void promoteToAdmin(){
        this.role = Role.ADMIN;
    }

    public Long getId() { return id; }
    public UUID getPublicId() { return publicId;}
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phone; }
    public String getHashedPassword() { return hashedPassword; }
    public Role getRole() { return role; }
}