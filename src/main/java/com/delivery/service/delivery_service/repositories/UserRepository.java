package com.delivery.service.delivery_service.repositories;

import com.delivery.service.delivery_service.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByLogin(String login);
}
