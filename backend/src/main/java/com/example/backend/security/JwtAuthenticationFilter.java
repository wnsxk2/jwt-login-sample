//package com.example.backend.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final AuthenticationManager authenticationManager;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = "";
//        try{
//            // 요청에서 토큰 가져오기
//            token = getToken(request);
//            if(StringUtils.hasText(token)){
//                getAuthentication(token);
//            }
//            filterChain.doFilter(request, response);
//        } catch (Exception e) {
//            // error log
//            log.debug(e.getMessage());
//        }
//    }
//
//    private void getAuthentication(String token) {
//        // security 인증 완료
//        // SecurityContextHolder에 등록해야 인증된 사용자라고 인식한다.
//        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(token);
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authenticate);
//    }
//
//    private String getToken(HttpServletRequest request) {
//        String authorization = request.getHeader("Authorization");
//        if(StringUtils.hasText(authorization) && authorization.startsWith("Bearer")){
//            String[] arr = authorization.split(" ");
//            return arr[1];
//        }
//        return null;
//    }
//}
