package com.purna.filter;

import com.purna.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil util;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Check if the route is secured
            if (validator.isSecured.test(request)) {
                // Check for Authorization header
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Authorization header not found");
                }

                // Extract token from Authorization header
                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new RuntimeException("Invalid Authorization header");
                }

                String token = authHeader.substring(7);

                try {
                    util.validateToken(token);

                    request = exchange.getRequest().mutate()
                            .header("loggedInUser", util.extractUsername(token))
                            .build();

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Unauthorized access to application");
                }
            }

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    public static class Config {
    }
}
