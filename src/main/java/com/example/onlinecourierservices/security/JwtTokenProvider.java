package com.example.onlinecourierservices.security;

import com.example.onlinecourierservices.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.key}")
    private String JWT_SECRET_KEY_FOR_TOKEN;
    @Value("${jwt.token.ttl}")
    private Long JWT_EXPIRED_TIME_FOR_TOKEN;


    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRED_TIME_FOR_TOKEN))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY_FOR_TOKEN)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET_KEY_FOR_TOKEN)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }



}
