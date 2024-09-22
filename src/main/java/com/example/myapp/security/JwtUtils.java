package com.example.myapp.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.UnsupportedKeyException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${security.jwt.secret}")
    private String secretString;

    @Value("${security.jwt.expirationMs}")
    private int jwtExpirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
         key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
    }

    public String signJwtToken(String username) {
        return Jwts
                .builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public boolean verifyJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key).build().parse(authToken);
            return true;
        } catch (SignatureException e) {
            // Invalid signature
        } catch (MalformedJwtException e) {
            // Malformed token
        } catch (ExpiredJwtException e) {
            // Token expired
        } catch (UnsupportedKeyException e) {
            // Unsupported key
        } catch (IllegalArgumentException e) {
            // Empty token
        }

        return false;
    }
}
