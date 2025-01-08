package com.example.backend.security;

import com.example.backend.vo.UserVO;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class TokenProvider {
    // application.properties로 관리
    private static final String SECRET_KEY = "iNmvkX9S6TfwN2ls8AhEON8Gcm331HfxiNmvkX9S6TfwN2ls8AhEON8Gcm331Hfx";
    private static final long EXPIRATION_TIME = 1000 * 60 * 30; // 30분

    public static String generateToken(UserVO user) {

        return Jwts.builder()
                // payload에 들어갈 내용
                .claims()
                .subject(user.getId())
                .add("username", user.getUsername())
                .add("password", user.getPassword())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .and()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    //토큰에서 userName 꺼내오는 메서드
    public static String getUserName(String token) {
        return extractClaims(token).get("username").toString();
    }

    private static Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    public static UserDetails getUser(String token) {
        String username = extractClaims(token).get("username").toString();
        String password = extractClaims(token).get("password").toString();

        return User.builder().username(username).password(password).build();
    }

    /**
     * JWT 검증
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    //토큰 만료확인 메서드
    public static boolean isExpired(String token) {
        // expire timestamp를 return함
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate.before(new Date());
    }
}
