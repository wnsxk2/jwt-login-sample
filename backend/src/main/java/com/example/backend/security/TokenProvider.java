package com.example.backend.security;

import com.example.backend.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class TokenProvider {
    // secret key application.properties로 관리
    private static final String SECRET_KEY = "iNmvkX9S6TfwN2ls8AhEON8Gcm331HfxiNmvkX9S6TfwN2ls8AhEON8Gcm331Hfx";
    private final long EXPIRATION_TIME = 1000 * 60 * 30; // 15분

    public String generateToken(UserVO user) {
        return Jwts.builder()
                // payload에 들어갈 내용
                .setSubject(user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                // header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }
}
