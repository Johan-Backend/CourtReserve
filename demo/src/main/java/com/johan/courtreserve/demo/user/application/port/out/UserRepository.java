package com.johan.courtreserve.demo.user.application.port.out;

import java.util.Optional;

import com.johan.courtreserve.demo.user.domain.model.User;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(User id);
    boolean existByEmail(String email);
}