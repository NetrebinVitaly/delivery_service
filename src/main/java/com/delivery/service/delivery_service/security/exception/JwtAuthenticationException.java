package com.delivery.service.delivery_service.security.exception;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtAuthenticationException extends AuthenticationException {

    HttpStatus status;

    public JwtAuthenticationException(String msg) {
        super(msg);
    }

    public JwtAuthenticationException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
