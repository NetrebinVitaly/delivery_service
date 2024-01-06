package com.delivery.service.delivery_service.security.JwtProvider;

import com.delivery.service.delivery_service.security.detailServices.ProjectUserDetailsService;
import com.delivery.service.delivery_service.security.exception.JwtAuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class JwtProvider {

    final ProjectUserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    String secret;

    @Value("${jwt.header}")
    String header;

    @Value("${jwt.lifetime}")
    Duration jwtLifeTime;

    public String createToken(String login, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", role);
        return  Jwts.builder()
                .claims(claims)
                .subject(login)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtLifeTime.toMillis()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean tokenIsValid(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secret)
                    .build().parseSignedClaims(token);
            return !claimsJws.getPayload().getExpiration().before(new Date(System.currentTimeMillis()));
        } catch (JwtException | IllegalArgumentException exception) {
            throw new JwtAuthenticationException("JWT is invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuth(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getLogin(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getLogin(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest servletRequest) {

        return servletRequest.getHeader(header);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
