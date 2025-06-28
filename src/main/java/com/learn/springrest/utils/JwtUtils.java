package com.learn.springrest.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtils {


    private final SecretKey signingKey;

    public JwtUtils(@Value("${security.jwt.secret}") String secretKeyBase64) {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(secretKeyBase64);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    @Value("${security.jwt.expiration}")
    private long EXPIRATION_TIME;


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(EXPIRATION_TIME)))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

}
