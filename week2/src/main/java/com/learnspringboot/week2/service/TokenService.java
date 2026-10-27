package com.learnspringboot.week2.service;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.learnspringboot.week2.config.JwtConfig;

@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;
    public static final long TOKEN_EXPIRATION_SECONDS = 3600; // 1 hour

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(Duration.ofSeconds(TOKEN_EXPIRATION_SECONDS));

        String scope = authentication.getAuthorities().stream().map(authority -> authority.getAuthority())
                .map(authority -> authority.replace("ROLE_", "")).collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder().issuer(JwtConfig.ISSUER).issuedAt(issuedAt).expiresAt(expiresAt)
                .subject(authentication.getName()).claim("scope", scope).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
