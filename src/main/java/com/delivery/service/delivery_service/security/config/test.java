//package com.delivery.service.delivery_service.security.config;
//
//import com.delivery.service.delivery_service.entities.enums.Role;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class test {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers(HttpMethod.GET, "/courier/**").hasAuthority(Role.COURIER.name())
//                        .requestMatchers(HttpMethod.POST, "/courier/**").hasAuthority(Role.COURIER.name())
//                        .requestMatchers(HttpMethod.PATCH, "/courier/**").hasAuthority(Role.COURIER.name())
//
//                        .requestMatchers(HttpMethod.GET, "/client/**").hasAuthority(Role.CLIENT.name())
//                        .requestMatchers(HttpMethod.POST, "/client/**").hasAuthority(Role.CLIENT.name())
//                        .requestMatchers(HttpMethod.PATCH, "/client/**").hasAuthority(Role.CLIENT.name())
//
//                        .requestMatchers(HttpMethod.GET, "/admin/**").hasAuthority(Role.ADMIN.name())
//                        .requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority(Role.ADMIN.name())
//                        .requestMatchers(HttpMethod.PATCH, "/admin/**").hasAuthority(Role.ADMIN.name())
//
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(
////                        Customizer.withDefaults()
//                        login -> login.
//                                .defaultSuccessUrl("/swagger-ui/index.html"))
//                .logout(logout -> logout
//                        .clearAuthentication(true)
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .logoutSuccessUrl("/login")
//                )
//
//        ;
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            @Qualifier("projectUserDetailsService")
//            UserDetailsService userDetailsService,
//            PasswordEncoder passwordEncoder) {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder);
//
//        ProviderManager providerManager = new ProviderManager(authenticationProvider);
//        providerManager.setEraseCredentialsAfterAuthentication(false);
//
//        return providerManager;
//    }
//
//    @Bean
//    @Primary
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder(8);
//    }
//}
