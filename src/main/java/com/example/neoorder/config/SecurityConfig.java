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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filter chain...");
        
        http
            .csrf().disable()
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
                // Allow static resources
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
                    "/error"
                ).permitAll()
                // Allow authentication endpoints
                .requestMatchers("/api/auth/**").permitAll()
                // Allow H2 console
                .requestMatchers("/h2-console/**").permitAll()
                // Require authentication for all other requests
                .anyRequest().authenticated()
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable()) // For H2 console
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    logger.warn("Unauthorized access attempt: {}", request.getRequestURI());
                    response.sendRedirect("/login.html");
                })
            );

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
