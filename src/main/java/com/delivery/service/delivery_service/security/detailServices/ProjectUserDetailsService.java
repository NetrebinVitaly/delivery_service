package com.delivery.service.delivery_service.security.detailServices;

import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.repositories.UserRepository;
import com.delivery.service.delivery_service.security.details.ProjectUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserEntity> user = repository.findByLogin(login);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return new ProjectUserDetails(user.get());
    }
}
