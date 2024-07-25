package com.purna.config;

import com.purna.service.JwtServiceImpl;
import com.purna.service.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtServiceImpl service;
    @Autowired
    private UserDetailsService impl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = service.getUserNameFromToken(token);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = impl.loadUserByUsername(username);
            if (service.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationFilter = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationFilter.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationFilter);

                // Generate a new token
                String newToken = service.generateToken(userDetails.getUsername());
                //System.out.println("new Token : "+newToken);
                //logger.info(newToken);

                // Add the new token to the response header
                response.setHeader("Authorization", "Bearer " + newToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
