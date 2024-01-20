package com.delivery.service.delivery_service.services;

import com.delivery.service.delivery_service.dto.UserDto;
import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.entities.enums.Role;

public interface UserService extends Service<UserEntity, UserDto, String>{
    UserEntity create(UserDto entity, Role role);
}
