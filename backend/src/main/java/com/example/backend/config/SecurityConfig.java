package com.example.backend.config;

import com.example.backend.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화 (테스트용)
                .csrf(csrf ->
                        csrf.disable())
                // token 인증을 사용하기 때문에 basic 인증 비활성화
//                .httpBasic(httpBasic ->
//                        httpBasic.disable())
                // 경로 인증
                .authorizeHttpRequests((requests) ->
                        requests
                                // default, home 경로는 인증 제외
                                .requestMatchers( "/home", "/signin", "/signup").permitAll()
                                // 나머지 경로는 모두 인증 필요
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/signin")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .loginProcessingUrl("/login-Proc")
                                .defaultSuccessUrl("/", true)
                )
                .logout(logout ->
                        logout.logoutSuccessUrl("/logout")
                );
//                .addFilterBefore( new JwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
        // session 기반이 아님을 선언
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
