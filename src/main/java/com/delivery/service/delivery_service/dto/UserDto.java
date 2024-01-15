package com.delivery.service.delivery_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    @Schema(description = "Username", example = "User")
    @Size(min = 5, max = 50, message = "Username must contain from 5 to 50 characters")
    @NotBlank(message = "Username cannot be empty")
    String login;

    @Schema(description = "E-mail address", example = "example@gmail.com")
    @Size(min = 5, max = 50, message = "The email address must contain between 5 and 255 characters")
    @NotBlank(message = "Email address cannot be empty")
    @Email(message = "Email address must be in the format user@example.com")
    String email;

    @Schema(description = "Password", example = "password123")
    @Size(max = 500, message = "Password length must be no more than 255 characters")
    String password;
}