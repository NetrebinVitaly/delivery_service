package com.delivery.service.delivery_service.services;

import com.delivery.service.delivery_service.dto.UserDto;
import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.entities.enums.Role;
import com.delivery.service.delivery_service.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder bCryptPasswordEncoder;

    public UserEntity createUser(UserDto dto, Role role) {
        log.info("Create {} in DB", dto.getEmail());
        return userRepository.saveAndFlush(UserEntity
                .builder()
                .email(dto.getEmail())
                .login(dto.getLogin())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .roles(role)
                .build());
    }

    public UserEntity registration(UserDto dto) {
        return userRepository.save(UserEntity
                .builder()
                .email(dto.getEmail())
                .login(dto.getLogin())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .roles(Role.CLIENT)
                .build());
    }

    public UserEntity getUserByLogin(String login) {
        return userRepository.findByLogin(login).get();
    }
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}


