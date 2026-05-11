package com.johan.courtreserve.demo.auth.infrastructure.web.dto;

import com.johan.courtreserve.demo.auth.application.service.SignUpCommand;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "The name is required.")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]{3,}$", message = "The name cannot contain numbers or special characters.")
    private String name;

    @NotBlank(message = "The last name is required.")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]{3,}$", message = "The last name cannot contain numbers or special characters.")
    private String lastName;

    @NotBlank(message = "The email is required.")
    @Email(message = "The email must be valid.")
    private String email;

    @Pattern(regexp = "^(\\+34)?[6-9][0-9]{8}$", message = "the phone number cannot contain letters and 9 digits.")
    private String phone;

    @NotBlank(message = "The password is required.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$", message = "The password must contain at least one uppercase letter, one number, and one special character.")
    private String password;

    public SignUpCommand toCommand(){
        return new SignUpCommand(name, lastName, email, phone, password);
    }
}