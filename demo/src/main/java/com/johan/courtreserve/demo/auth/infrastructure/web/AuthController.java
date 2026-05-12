package com.johan.courtreserve.demo.auth.infrastructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johan.courtreserve.demo.auth.application.service.AuthService;
import com.johan.courtreserve.demo.auth.infrastructure.web.dto.LoginRequest;
import com.johan.courtreserve.demo.auth.infrastructure.web.dto.SignUpRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest request){
        authService.signUp(request.toCommand());
        return ResponseEntity.ok(null);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(null);
    }
}