package com.johan.courtreserve.demo.user.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserJpaRepository extends JpaRepository<UserJpaEntity, Long>{
    Optional<UserJpaEntity> findByPublicId(UUID publicId);
    Optional<UserJpaEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}