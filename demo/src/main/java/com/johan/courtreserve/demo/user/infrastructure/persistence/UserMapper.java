package com.johan.courtreserve.demo.user.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.johan.courtreserve.demo.user.domain.model.User;

@Component
public class UserMapper {
    public UserJpaEntity toJpaEntity(User user) {
        return new UserJpaEntity(
            user.getId(),               
            user.getPublicId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getHashedPassword()
        );
    }
    
    public User toDomain(UserJpaEntity entity) {
        return User.reconstitute(
            entity.getId(),
            entity.getPublicId(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getEmail(),
            entity.getPhoneNumber(),
            entity.getHashedPassword()
        );
    }
}