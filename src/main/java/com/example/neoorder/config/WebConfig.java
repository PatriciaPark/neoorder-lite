package com.example.neoorder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                logger.debug("Processing request in WebConfig Interceptor: {} {}", request.getMethod(), request.getRequestURI());
                
                // Attempt to clear Vary to avoid conflicts, then set specific CORS headers
                response.setHeader("Vary", null); 
                
                String origin = request.getHeader("Origin");
                // Only allow your specific frontend origin for localhost and production
                if (origin != null && (origin.equals("http://localhost:8080") || origin.equals("https://neoorder-lite.onrender.com"))) {
                    response.setHeader("Access-Control-Allow-Origin", origin);
                    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization, X-Requested-With");
                    response.setHeader("Access-Control-Allow-Credentials", "true");
                    response.setHeader("Access-Control-Max-Age", "3600");
                } else if (origin != null) {
                    logger.debug("Origin {} not explicitly allowed by WebConfig interceptor.", origin);
                }

                // Handle preflight requests directly in the interceptor
                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    // Ensure CORS headers are set for OPTIONS even if origin check was intermediate
                    if (origin != null && (origin.equals("http://localhost:8080") || origin.equals("https://neoorder-lite.onrender.com"))) {
                         // Re-set headers for OPTIONS if they were set above, to be sure
                        response.setHeader("Access-Control-Allow-Origin", origin);
                        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization, X-Requested-With");
                        response.setHeader("Access-Control-Allow-Credentials", "true");
                        response.setHeader("Access-Control-Max-Age", "3600");
                    }
                    response.setStatus(HttpServletResponse.SC_OK);
                    logger.debug("Preflight OPTIONS request handled by WebConfig Interceptor, sending SC_OK");
                    return false; // Stop further processing for OPTIONS
                }
                return true;
            }
        }).order(Ordered.HIGHEST_PRECEDENCE); // Interceptor itself ordered high
    }
} 