package com.example.backend.security;

import com.example.backend.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        log.info("authorizationHeader:{}", authorizationHeader);

        // header에서 jwt토큰 꺼내기기
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // token 분리
        String token;
        try {
            //Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6Imt5ZW9uZ3JvazUiLCJpYXQiOjE2Njk2NT ~~~
            //형태로 들어오므로 .split(“ “)로 token을 분리 한다.
            token = authorizationHeader.split(" ")[1];
        } catch (Exception e) {
            log.error("token 추출에 실패 했습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // Token 유효성 검증
        if(!TokenProvider.validateToken(token)) {
            filterChain.doFilter(request, response);
           return;
        }

        // Token이 만료 되었는지 Check
        // 만료 되었다면 refresh token 요청..
        if(TokenProvider.isExpired(token)){
            filterChain.doFilter(request, response);
            return;
        };

        UserDetails user = TokenProvider.getUser(token);

        if(user != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
