package com.johan.courtreserve.demo.auth.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.johan.courtreserve.demo.user.application.port.out.UserRepository;
import com.johan.courtreserve.demo.user.domain.exception.UserAlreadyExistException;
import com.johan.courtreserve.demo.user.domain.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passEncoder;

    public void signUp(SignUpCommand command){
        if (userRepository.existByEmail(command.email())) throw new UserAlreadyExistException(command.email());

        User newUser = User.create(null, null, null, null, passEncoder.encode(command.password()));
    }
}