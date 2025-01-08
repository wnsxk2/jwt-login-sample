package com.example.backend.config;

import com.example.backend.security.JwtAuthenticationFilter;
import com.example.backend.security.TokenProvider;
import com.example.backend.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화 (테스트용)
                .csrf(csrf ->
                        csrf.disable())
                // token 인증을 사용하기 때문에 basic 인증 비활성화
                .httpBasic(httpBasic ->
                        httpBasic.disable())
                // 경로 인증
                .authorizeHttpRequests((requests) ->
                        requests
                                // default, home 경로는 인증 제외
                                .requestMatchers( "/home", "/signin", "/signup").permitAll()
                                // 모든 경로에 대해 인증처리 pass
//                                .anyRequest().permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin.disable()
                )
//                .formLogin(formLogin ->
//                        formLogin
//                                .loginPage("/signin")
//                                .usernameParameter("username")
//                                .passwordParameter("password")
//                                .loginProcessingUrl("/login-Proc")
//                                .defaultSuccessUrl("/", true)
//                )
//                .logout(logout ->
//                        logout.logoutSuccessUrl("/logout")
//                )
//                .userDetailsService(myUserDetailsService)
                // session 기반이 아님을 선언
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore( new JwtAuthenticationFilter(customUserDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
