package org.example.middleware;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证中间件
 */
@Component
public class AuthMiddleware implements Filter {
    
    @Value("${app.auth.token:}")
    private String authToken;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // 检查是否需要认证的路径
        String requestURI = httpRequest.getRequestURI();
        if (requiresAuth(requestURI)) {
            String authorization = httpRequest.getHeader("Authorization");
            
            if (authorization == null) {
                sendUnauthorizedResponse(httpResponse, "Authorization Unauthorized");
                return;
            }
            
            String[] parts = authorization.split(" ");
            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                sendUnauthorizedResponse(httpResponse, "Bearer Unauthorized");
                return;
            }
            
            String token = parts[1];
            if (authToken == null || authToken.isEmpty() || !authToken.equals(token)) {
                sendUnauthorizedResponse(httpResponse, "token Unauthorized {token=" + token + ", authToken=" + authToken + "}");
                return;
            }
        }
        
        chain.doFilter(request, response);
    }
    
    private boolean requiresAuth(String requestURI) {
        return requestURI.startsWith("/user/list") || requestURI.startsWith("/org/list");
    }
    
    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        ApiResponse<Object> apiResponse = ApiResponse.error(message);
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
    }
}