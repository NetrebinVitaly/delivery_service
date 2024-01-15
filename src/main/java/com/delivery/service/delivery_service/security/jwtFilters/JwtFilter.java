package com.delivery.service.delivery_service.security.jwtFilters;

import com.delivery.service.delivery_service.security.JwtUtils.JwtUtils;
import com.delivery.service.delivery_service.security.exception.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtFilter extends OncePerRequestFilter {

    JwtUtils jwtUtils;

    @Override
    public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtUtils.resolveToken(servletRequest);
        if (token == null ||!token.startsWith("Bearer ")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        token = token.substring(7);
        try {
            if (token != null && jwtUtils.tokenIsValid(token)) {

                Authentication authentication = jwtUtils.getAuth(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            servletResponse.sendError(e.getStatus().value());
            throw new JwtAuthenticationException("JWT is invalid");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}