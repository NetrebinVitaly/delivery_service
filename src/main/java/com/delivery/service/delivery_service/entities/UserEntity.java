package com.delivery.service.delivery_service.entities;


import com.delivery.service.delivery_service.entities.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users"
        , schema = "delivery_schema")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotNull
    @Column(name = "login", nullable = false, length = 50)
    String login;

    @NotNull
    @Column(name = "email", unique = true, nullable = false, length = 50)
    String email;


    @Column(name = "password", nullable = false, length = 500)
    String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role roles;

}
