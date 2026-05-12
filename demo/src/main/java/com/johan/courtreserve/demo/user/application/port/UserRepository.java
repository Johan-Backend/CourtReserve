package com.johan.courtreserve.demo.user.application.port;

import java.util.Optional;
import java.util.UUID;

import com.johan.courtreserve.demo.user.domain.model.User;

// Output Port
public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByPublicId(UUID publicId);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}