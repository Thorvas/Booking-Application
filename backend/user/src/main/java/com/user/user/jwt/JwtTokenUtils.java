package com.user.user.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(Long id) {

        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(jwtSecret.getBytes()))
                .compact();
    }
}
