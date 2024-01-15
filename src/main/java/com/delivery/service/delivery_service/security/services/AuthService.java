package com.delivery.service.delivery_service.security.services;


import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.entities.enums.Role;
import com.delivery.service.delivery_service.exceptions.BadRequestException;
import com.delivery.service.delivery_service.repositories.UserRepository;
import com.delivery.service.delivery_service.security.JwtUtils.JwtUtils;
import com.delivery.service.delivery_service.security.dto.AuthRequest;
import com.delivery.service.delivery_service.security.dto.AuthResponse;
import com.delivery.service.delivery_service.security.dto.RegistrationRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    AuthenticationManager manager;
    UserRepository repository;
    JwtUtils jwtUtils;
    PasswordEncoder encoder;

    public AuthResponse loginUser(AuthRequest request){
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        UserEntity user = repository
                .findByLogin(request.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtUtils.createToken(
                request.getLogin(),
                user.getRoles().name()
        );

        return new AuthResponse(token);
    }

    public AuthResponse createUser(RegistrationRequest request){
        if (!repository.findByLogin(request.getLogin()).isPresent()){
            UserEntity user = UserEntity
                    .builder()
                    .login(request.getLogin())
                    .email(request.getEmail())
                    .password(encoder.encode(request.getPassword()))
                    .roles(Role.CLIENT)
                    .build();
            repository.save(user);//Save new user in db
            var token = jwtUtils.createToken(user.getLogin(), user.getPassword());
            return new AuthResponse(token);
        }
        throw new BadRequestException("User already exists");
    }

}
