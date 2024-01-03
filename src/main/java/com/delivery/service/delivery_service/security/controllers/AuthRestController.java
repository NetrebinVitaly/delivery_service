package com.delivery.service.delivery_service.security.controllers;

import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.repositories.UserRepository;
import com.delivery.service.delivery_service.security.JwtProvider.JwtProvider;
import com.delivery.service.delivery_service.security.dto.AuthRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthRestController {

    AuthenticationManager manager;
    UserRepository repository;
    JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDto dto) {

        manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword()));
        UserEntity user = repository.findByLogin(dto.getLogin()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtProvider.createToken(dto.getLogin(), user.getRoles().name());

        Map<Object, Object> responseMessage = new HashMap<>();
        responseMessage.put("login", dto.getLogin());
        responseMessage.put("token", token);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }




    @PostMapping("/logout")
    public void logout(HttpServletResponse response, HttpServletRequest request) {
        SecurityContextLogoutHandler contextLogoutHandler = new SecurityContextLogoutHandler();
        contextLogoutHandler.logout(request, response, null);
    }

}
