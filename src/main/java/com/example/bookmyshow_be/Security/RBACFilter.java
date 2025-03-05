package com.example.bookmyshow_be.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RBACFilter extends OncePerRequestFilter {
    private RBACConfig rbacConfig;

    public RBACFilter(RBACConfig rbacConfig) {
        this.rbacConfig = rbacConfig;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if (isAuthenticationEndpoint(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String userType = (String) request.getAttribute("userType");

        if (userType == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("User type not found in token");
            return;
        }

        if (!isAuthorized(userType, requestURI, method)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access denied");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAuthenticationEndpoint(String path) {
        return path.contains("/login") || path.contains("/register") || path.contains("/signup");
    }

    private boolean isAuthorized(String userType, String path, String method) {
        return rbacConfig.hasAccess(userType, path, method);
    }
}
