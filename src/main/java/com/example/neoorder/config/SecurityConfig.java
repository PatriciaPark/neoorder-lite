package com.example.neoorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 🔥 CSRF 보호 해제: API POST 요청이 막히지 않도록 함
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(authz -> authz
                // ✅ REST API는 인증 없이 접근 허용
                .requestMatchers("/api/**").permitAll()

                // ✅ 정적 리소스 및 프론트 HTML 접근 허용
                .requestMatchers(
                    "/orders.html", 
                    "/new-order.html", 
                    "/login.html", 
                    "/css/**", 
                    "/js/**", 
                    "/images/**"
                ).permitAll()

                // ✅ 그 외는 인증 필요
                .anyRequest().authenticated()
            )

            // ✅ 로그인 설정
            .formLogin(form -> form
                .loginPage("/login.html")
                .permitAll()
            )

            // ✅ 로그아웃 허용
            .logout(logout -> logout.permitAll());

        return http.build();
    }
}
