package de.neuefische.devquiz.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

@Service
public class JWTUtilService {

    @Value("${neuefische.devquiz.jwt.secret}")
    private String JWT_SECRET;

    public String createToken(HashMap<String, Object> claims, String subject){

        //Generate JWT
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(4))))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }
}
