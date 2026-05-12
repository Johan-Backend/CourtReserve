package com.johan.courtreserve.demo.user.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.johan.courtreserve.demo.user.application.port.UserRepository;
import com.johan.courtreserve.demo.user.domain.model.User;

import lombok.RequiredArgsConstructor;

//Output Adapter
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepository{
    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    @Override
    public User save(User user) {
        UserJpaEntity entity = mapper.toJpaEntity(user);
        UserJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);  
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public Optional<User> findByPublicId(UUID publicId) {
        return jpaRepository.findByPublicId(publicId).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}