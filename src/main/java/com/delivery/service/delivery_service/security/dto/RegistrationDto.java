package com.delivery.service.delivery_service.security.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationDto {
    String login;
    String email;
    String password;
}
