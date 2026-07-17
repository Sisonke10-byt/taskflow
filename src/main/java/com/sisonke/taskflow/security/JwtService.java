package com.sisonke.taskflow.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "your-super-secret-key-that-is-at-least-32-characters-long";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {

        Claims claims = Jwts.parser()
            .verifyWith((SecretKey) getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();

        return claims.getSubject();
   }
    public Date extractExpiration(String token) {

        Claims claims = Jwts.parser()
            .verifyWith((SecretKey) getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();

        return claims.getExpiration();
    }
    private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
}

public boolean validateToken(String token, String email) {

    final String extractedEmail = extractEmail(token);

    return extractedEmail.equals(email)
            && !isTokenExpired(token);
}
}