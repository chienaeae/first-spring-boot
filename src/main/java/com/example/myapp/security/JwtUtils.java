package com.example.myapp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.UnsupportedKeyException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;




@Component
public class JwtUtils {
    public enum TokenType {
        ACCESS,
        REFRESH
    }

    private static final String TOKEN_TYPE = "tokenType";

    @Value("${security.jwt.secret}")
    private String secretString;

    @Value("${security.jwt.accessTokenExpirationMs}")
    private long jwtAccessTokenExpirationMs;

    @Value("${security.jwt.refreshTokenExpirationMs}")
    private long jwtRefreshTokenExpirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
         key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
    }

    public String generateJwtAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE, TokenType.ACCESS.name());
        return signJwtToken(username, jwtAccessTokenExpirationMs, claims);
    }

    public String generateJwtRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE, TokenType.REFRESH.name());
        return signJwtToken(username, jwtRefreshTokenExpirationMs, claims);
    }

    private String signJwtToken(String username, long duration, Map<String, Object> claims) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + duration))
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

    public boolean verifyJwtToken(String authToken, TokenType tokenType) {
        try {
            Jwts.parser().require(TOKEN_TYPE, tokenType.name()).verifyWith(key).build().parse(authToken);
            return true;
        } catch (SignatureException e) {
            // Invalid signature
        } catch (MalformedJwtException e) {
            // Malformed token
        } catch (ExpiredJwtException e) {
            // Token expired
        } catch (InvalidClaimException e) {
            // Invalid claim
        } catch (UnsupportedKeyException e) {
            // Unsupported key
        } catch (IllegalArgumentException e) {
            // Empty token
        }

        return false;
    }
}
