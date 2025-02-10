package com.guna.yumzoom.security;

import com.guna.yumzoom.user.MyUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final ApplicationContext context;
    private final JwtService jwtService;
    private final JwtBlockListService jwtBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String mailID = null;
        String role= null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            mailID = jwtService.extractMailId(token);
            role = jwtService.extractRoles(token).getFirst();

            if(request.getRequestURI().contains("/restaurant") && !role.equals("RESTAURANT")){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access Denied: Cannot access restaurant pages");
                return;
            } else if (request.getRequestURI().contains("/agent") && !role.equals("DELIVERY_AGENT")){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access Denied: Cannot access agent pages");
                return;
            } else if (request.getRequestURI().contains("/customer") && !role.equals("CUSTOMER")){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access Denied: Cannot access customer pages");
                return;
            }
        }

        if (mailID != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Check if the token is blacklisted
            if (jwtBlacklistService.isTokenBlacklisted(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Please login again.");
                return;  // Deny access if token is blacklisted
            }

            UserDetails userDetails = context.getBean(MyUserDetailService.class).loadUserByUsername(mailID);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
