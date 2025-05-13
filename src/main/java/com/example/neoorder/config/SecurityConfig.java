package com.example.neoorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import com.example.neoorder.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filter chain...");
        
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .securityContext(context -> context
                .securityContextRepository(securityContextRepository())
                .requireExplicitSave(true)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/login.html")
                .sessionRegistry(sessionRegistry())
            )
            .authorizeHttpRequests(auth -> auth
                // 정적 리소스 및 기본 페이지 접근 허용
                .requestMatchers(
                    "/", 
                    "/index.html", 
                    "/login.html", 
                    "/orders.html",
                    "/style.css",
                    "/js/**", 
                    "/css/**", 
                    "/images/**", 
                    "/*.ico", 
                    "/favicon.ico",
                    "/error",
                    "/swagger-ui/**", 
                    "/v3/api-docs/**",
                    "/h2-console/**"
                ).permitAll()
                // 인증 관련 API 접근 허용
                .requestMatchers(
                    "/api/auth/login",
                    "/api/auth/check",
                    "/api/auth/logout"
                ).permitAll()
                // 주문 조회 API 접근 허용
                .requestMatchers(HttpMethod.GET,
                    "/api/orders",
                    "/api/orders/status/*"
                ).permitAll()
                // 주문 수정/삭제는 관리자만 허용
                .requestMatchers(
                    "/api/orders/*/status",
                    "/api/orders/*/cancel",
                    "/api/orders/*/duplicate"
                ).hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/orders").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/orders/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/orders/*").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .exceptionHandling(eh -> eh.authenticationEntryPoint((request, response, authException) -> {
                response.sendRedirect("/index.html");
            }));

        logger.info("Security filter chain configured successfully");
        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        logger.info("Creating security context repository...");
        HttpSessionSecurityContextRepository repository = new HttpSessionSecurityContextRepository();
        repository.setDisableUrlRewriting(true);
        return repository;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        logger.info("Creating HTTP session event publisher...");
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        logger.info("Configuring CORS...");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:8080", 
            "http://127.0.0.1:8080",
            "https://neoorder-lite.onrender.com"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "Accept",
            "Origin",
            "X-Requested-With",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers",
            "X-XSRF-TOKEN",
            "Cookie"
        ));
        configuration.setExposedHeaders(Arrays.asList(
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials",
            "Set-Cookie",
            "X-XSRF-TOKEN"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        logger.info("Creating authentication provider...");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(false);
        logger.info("Authentication provider created successfully");
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        logger.info("Creating authentication manager...");
        AuthenticationManager manager = authenticationConfiguration.getAuthenticationManager();
        logger.info("Authentication manager created: {}", manager);
        return manager;
    }
}
