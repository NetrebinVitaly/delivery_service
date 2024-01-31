package com.delivery.service.delivery_service.services;

import com.delivery.service.delivery_service.dto.UserDto;
import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.entities.enums.Role;
import com.delivery.service.delivery_service.exceptions.BadRequestException;
import com.delivery.service.delivery_service.repositories.UserRepository;
import com.delivery.service.delivery_service.utils.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder bCryptPasswordEncoder;
    @Mock
    ValidationUtil validationUtil;
    @InjectMocks
    UserService service;

    @Mock
    Authentication authentication;
    @Mock
    SecurityContext context;

    @BeforeEach
    public void init() {
        SecurityContextHolder.setContext(context);
    }

    @Test
    void createUser_ReturnUserEntity() {
        var dto = new UserDto("testUser", null, null);
        var role = Role.CLIENT;
        var user = UserEntity
                .builder()
                .login(dto.getLogin())
                .roles(role)
                .build();
        when(userRepository.saveAndFlush(user)).thenReturn(user);

        var result = service.createUser(dto, role);

        assertNotNull(result);
        assertEquals(result, user);
        verify(userRepository, times(1)).saveAndFlush(user);
    }

    @Test
    void createUser_ReturnBadRequestException() {
        var dto = new UserDto("testUser", null, null);
        var role = Role.CLIENT;
        var user = UserEntity
                .builder()
                .login(dto.getLogin())
                .roles(role)
                .build();
        when(userRepository.findByLogin(dto.getLogin()))
                .thenReturn(Optional.of(user));

        var result = assertThrows(
                BadRequestException.class,
                ()-> service.createUser(dto, role));
    }


    @Test
    void getBy_ReturnUserEntity() {
        var testUser = UserEntity
                .builder()
                .login("testUser")
                .build();

        when(userRepository.findByLogin(testUser.getLogin()))
                .thenReturn(Optional.of(testUser));

        var result = service.getUserByLogin(testUser.getLogin());

        assertNotNull(result);
        assertEquals(result, testUser);
        verify(userRepository, times(1))
                .findByLogin(testUser.getLogin());

    }

    @Test
    void getAll_ReturnListUsers() {
        List<UserEntity> testUsers = List.of(
                new UserEntity()
        );

        when(userRepository.findAll()).thenReturn(testUsers);

        var result = service.getAllUsers();

        assertNotNull(result);
        assertEquals(result, testUsers);
        verify(userRepository, times(1)).findAll();
    }
}