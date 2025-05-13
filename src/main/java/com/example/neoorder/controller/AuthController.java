package com.example.neoorder.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.session.SessionRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.example.neoorder.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final SessionRegistry sessionRegistry;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, SessionRegistry sessionRegistry) {
        this.authenticationManager = authenticationManager;
        this.sessionRegistry = sessionRegistry;
        logger.info("AuthController initialized with AuthenticationManager: {}", authenticationManager);
    }

    @Operation(
        summary = "로그인 (Login)",
        description = "사용자 로그인을 수행합니다.\nAuthenticate user and start session.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "로그인 정보 (Login credentials)",
            required = true,
            content = @Content(
                schema = @Schema(example = "{\n  \"username\": \"testadmin\",\n  \"password\": \"testpass123\"\n}")
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그인 성공 (Success)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\n  \"username\": \"testadmin\",\n  \"role\": \"ROLE_ADMIN\",\n  \"sessionId\": \"1234567890\",\n  \"authenticated\": true\n}"))),
        @ApiResponse(responseCode = "401", description = "인증 실패 (Unauthorized)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\n  \"error\": \"Invalid credentials\"\n}")))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session, HttpServletResponse response) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            logger.info("Login attempt for user: {}", username);
            logger.debug("Session ID before authentication: {}", session.getId());
            logger.debug("Received credentials - username: {}, password length: {}", username, password != null ? password.length() : 0);

            if (username == null || password == null) {
                logger.warn("Login failed: username or password is null");
                return ResponseEntity.badRequest().body(Map.of("error", "Username and password are required"));
            }

            try {
                logger.debug("Creating authentication token for user: {}", username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
                logger.debug("Attempting authentication with token: {}", authToken);
                
                Authentication authentication = authenticationManager.authenticate(authToken);
                logger.info("Authentication successful for user: {}", username);
                
                // Set authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                // Store SecurityContext in session
                session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
                
                // Set session attributes
                session.setAttribute("username", username);
                session.setAttribute("role", authentication.getAuthorities().iterator().next().getAuthority());
                session.setAttribute("authenticated", true);

                // Set session cookie
                Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
                sessionCookie.setPath("/");
                sessionCookie.setHttpOnly(true);
                sessionCookie.setMaxAge(-1); // Session cookie
                sessionCookie.setDomain("neoorder-lite.onrender.com");
                sessionCookie.setComment("NeoOrder Session Cookie");
                sessionCookie.setSecure(true); // Required for HTTPS
                response.addCookie(sessionCookie);

                // Add session to registry
                sessionRegistry.registerNewSession(session.getId(), authentication.getPrincipal());

                // Set response headers for CORS
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Origin", "https://neoorder-lite.onrender.com");
                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, Authorization");

                // JWT 토큰 발급
                String token = jwtUtil.generateToken(username);

                logger.info("Login successful for user: {}", username);
                logger.info("Session ID: {}", session.getId());
                logger.info("Authentication: {}", authentication);
                logger.info("Authorities: {}", authentication.getAuthorities());
                logger.info("Session attributes: username={}, role={}, authenticated={}", 
                    session.getAttribute("username"),
                    session.getAttribute("role"),
                    session.getAttribute("authenticated"));

                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("token", token);
                responseBody.put("user", Map.of("username", username));
                responseBody.put("role", authentication.getAuthorities().iterator().next().getAuthority());
                responseBody.put("sessionId", session.getId());
                responseBody.put("authenticated", true);
                
                return ResponseEntity.ok(responseBody);
            } catch (AuthenticationException e) {
                logger.error("Authentication failed for user {}: {}", username, e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
            } catch (Exception e) {
                logger.error("Unexpected error during authentication for user {}: {}", username, e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred during authentication"));
            }
        } catch (Exception e) {
            logger.error("Unexpected error during login: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An unexpected error occurred during login"));
        }
    }

    @Operation(
        summary = "로그아웃 (Logout)",
        description = "현재 세션을 로그아웃합니다.\nLogout current session.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그아웃 성공 (Success)"),
        @ApiResponse(responseCode = "500", description = "서버 오류 (Server error)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\n  \"error\": \"An error occurred during logout\"\n}")))
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        try {
            logger.info("Logout attempt for session: {}", session.getId());
            
            // 세션 무효화
            session.invalidate();
            
            logger.info("Session invalidated successfully");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error during logout: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An error occurred during logout"));
        }
    }

    @Operation(
        summary = "인증 상태 확인 (Check Auth)",
        description = "현재 세션의 인증 상태를 확인합니다.\nCheck authentication status of current session.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "인증됨 (Authenticated)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\n  \"username\": \"testadmin\",\n  \"role\": \"ROLE_ADMIN\",\n  \"authenticated\": true\n}"))),
        @ApiResponse(responseCode = "401", description = "미인증 (Not authenticated)",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\n  \"error\": \"Not authenticated\"\n}")))
    })
    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(HttpSession session) {
        try {
            logger.debug("Checking authentication status for session: {}", session.getId());
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
                String username = (String) session.getAttribute("username");
                String role = (String) session.getAttribute("role");
                
                logger.info("User is authenticated: username={}, role={}", username, role);
                
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("username", username);
                responseBody.put("role", role);
                responseBody.put("authenticated", true);
                
                return ResponseEntity.ok(responseBody);
            } else {
                logger.info("User is not authenticated");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Not authenticated"));
            }
        } catch (Exception e) {
            logger.error("Error checking authentication status: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "An error occurred while checking authentication status"));
        }
    }
} 