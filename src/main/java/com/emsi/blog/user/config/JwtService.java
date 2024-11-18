package com.emsi.blog.user.config;

import java.security.Key;
import java.util.Base64;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY= "c982792bf5f30ed8be1b642d596d1459a85c79f2d3bf7e4fec6783e22e3acbbe";
    public String extractUsername(String token) {
    
        return null;

    }

    private String extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody().getSubject();
            }
        
            private Key getSignInKey() {
                byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
                return Keys.hmacShaKeyFor(keyBytes);
            }

}
