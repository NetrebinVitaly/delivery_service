package com.delivery.service.delivery_service.util;

import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserValidator implements Validator {

    UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserEntity user = (UserEntity) target;
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            errors.rejectValue("login", "", "User with the same name already exists");
        }
    }
}
