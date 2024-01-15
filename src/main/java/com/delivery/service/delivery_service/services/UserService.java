package com.delivery.service.delivery_service.services;

import com.delivery.service.delivery_service.dto.UserDto;
import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.entities.enums.Role;
import com.delivery.service.delivery_service.exceptions.BadRequestException;
import com.delivery.service.delivery_service.exceptions.NotFoundException;
import com.delivery.service.delivery_service.repositories.UserRepository;
import com.delivery.service.delivery_service.utils.ValidationUtil;
import jakarta.validation.Valid;
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
    ValidationUtil validationUtil;

    public UserEntity createUser(@Valid UserDto dto, Role role) {
        validationUtil.isValid(dto);
        if(userRepository.findByLogin(dto.getLogin()).isEmpty()){
            throw new BadRequestException("User already exists");
        }
        log.info("Create {} in DB", dto.getEmail());
        return userRepository.saveAndFlush(UserEntity
                .builder()
                .email(dto.getEmail())
                .login(dto.getLogin())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .roles(role)
                .build());
    }

    public UserEntity getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(()-> new NotFoundException("User not found exception"));
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}


