package com.delivery.service.delivery_service.security.jwtFilters;

import com.delivery.service.delivery_service.security.JwtProvider.JwtProvider;
import com.delivery.service.delivery_service.security.exception.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtFilter extends GenericFilterBean {

    JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String token = jwtProvider.resolveToken((HttpServletRequest) servletRequest);

        try {
            if (token != null && jwtProvider.tokenIsValid(token.replace("Bearer ", ""))) {
                Authentication authentication = jwtProvider.getAuth(token.replace("Bearer ", ""));
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(e.getStatus().value());
            throw new JwtAuthenticationException("JWT is invalid");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
