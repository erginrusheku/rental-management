package com.rental_management.security.security_config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

@Configuration
public class JWTUtil {


    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getKey())
                .compact();
    }


    public SecretKey getKey(){
        byte [] keyBytes = Decoders.BASE64.decode(SecurityConstants.SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String getUsernameFromToken(String token) {
        JwtParser parser = Jwts.parser().verifyWith(getKey()).build();

        Claims claims = parser.parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    public boolean  validateToken(String token) {

        Jwts.parser()
                .verifyWith(getKey()).build()
                .parseSignedClaims(token);
        return true;

    }
}
