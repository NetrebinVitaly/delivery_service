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
public class DefaultUserService implements UserService{
    UserRepository userRepository;
    PasswordEncoder bCryptPasswordEncoder;
    ValidationUtil validationUtil;

    @Override
    public UserEntity create(@Valid UserDto dto, Role role) {
        validationUtil.isValid(dto);
        if(userRepository.findByLogin(dto.getLogin()).isPresent()){
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

    @Override
    public void deleteBy(String login) {
        userRepository
                .delete(userRepository
                        .findByLogin(login)
                        .orElseThrow(()->new NotFoundException("User not found"))
                );
        log.info("User {} has been deleted", login);
    }

    @Override
    public UserEntity getBy(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(()-> new NotFoundException("User not found exception"));
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }
}


