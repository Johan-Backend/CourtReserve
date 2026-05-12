package com.johan.courtreserve.demo.auth.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.johan.courtreserve.demo.auth.domain.exception.InvalidCredentials;
import com.johan.courtreserve.demo.user.application.port.UserRepository;
import com.johan.courtreserve.demo.user.domain.exception.EmailAlreadyExistsException;
import com.johan.courtreserve.demo.user.domain.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passEncoder;

    public void signUp(SignUpCommand command){
        if (userRepository.existsByEmail(command.email())) throw new EmailAlreadyExistsException();
        User newUser = User.create(command.firstName(), command.lastName(), command.email(), command.phoneNumber(), passEncoder.encode(command.password()));
        userRepository.save(newUser);
    }

    public void login(LoginCommand command){
        User userStorage = userRepository.findByEmail(command.email()).orElseThrow(()-> new InvalidCredentials());
        if (!passEncoder.matches(userStorage.getHashedPassword(), command.password())) throw new InvalidCredentials();

        
    }
}