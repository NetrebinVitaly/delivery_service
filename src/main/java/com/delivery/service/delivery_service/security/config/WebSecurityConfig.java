package com.delivery.service.delivery_service.security.config;

import com.delivery.service.delivery_service.entities.enums.Role;
import com.delivery.service.delivery_service.security.detailServices.ProjectUserDetailsService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSecurityConfig {

    JwtConfig jwtConfig;
    ProjectUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.GET, "/api/courier/**").hasAuthority(Role.COURIER.name())
                        .requestMatchers(HttpMethod.POST, "/api/courier/**").hasAuthority(Role.COURIER.name())
                        .requestMatchers(HttpMethod.PATCH, "/api/courier/**").hasAuthority(Role.COURIER.name())

                        .requestMatchers(HttpMethod.GET, "/api/client/**").hasAuthority(Role.CLIENT.name())
                        .requestMatchers(HttpMethod.POST, "/api/client/**").hasAuthority(Role.CLIENT.name())
                        .requestMatchers(HttpMethod.PATCH, "/api/client/**").hasAuthority(Role.CLIENT.name())

                        .requestMatchers(HttpMethod.GET, "/api/admin/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/admin/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, "/api/admin/**").hasAuthority(Role.ADMIN.name())

                        .requestMatchers(HttpMethod.GET,"/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()

                        .anyRequest().permitAll()
                )
                .authenticationProvider(authenticationProvider())
                .with(jwtConfig, Customizer.withDefaults())
        ;

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    @Primary
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Authentication", createAPIKeyScheme()))
                .info(new Info().title("Delivery Service V1")
                        .version("1.0"));
    }
}
