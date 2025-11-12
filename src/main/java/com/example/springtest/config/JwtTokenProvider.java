package com.example.springtest.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.security.Key;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private final Set<String> allowedTokenSet = ConcurrentHashMap.newKeySet();

    private final Key secretKey = Keys.hmacShaKeyFor("12345678901234567890123456789012".getBytes()); // 최소 32byte

    public String  generateToken(String userId) {
        String token = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        allowedTokenSet.add(token);

        return token;
    }

    public String validateAndGetUserId(String token) {

        if(!allowedTokenSet.contains(token)) {
            return "";
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        }
        catch(JwtException e) {
            return "";
        }
    }

    // 토큰 삭제
    public void removeToken(String token) {
        allowedTokenSet.remove(token);
    }

    @Scheduled(fixedRate =  24 * 60 * 60 * 1000) // 하루마다
    public void prevetToken() {
        for(String token : allowedTokenSet) {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
            }
            catch(JwtException e) {
                allowedTokenSet.remove(token);
            }
        }
    }

}
