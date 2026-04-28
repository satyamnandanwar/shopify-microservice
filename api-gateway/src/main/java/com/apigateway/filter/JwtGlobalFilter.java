package com.apigateway.filter;

import com.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtGlobalFilter implements GlobalFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // ✅ 1. Skip auth endpoints
        if (path.startsWith("/auth/")
                || path.startsWith("/api/v1/product/list/categories")
                || path.startsWith("/api/v1/product/list/search")
                || path.startsWith("/api/v1/cart/add")) {
            return chain.filter(exchange);
        }

        // ✅ 2. Clean headers (prevent spoofing)
        ServerHttpRequest cleanedRequest = exchange.getRequest().mutate()
                .headers(h -> {
                    h.remove("X-User-Id");
                    h.remove("X-User-Role");
                })
                .build();

        // ✅ 3. Get Authorization header
        String header = cleanedRequest.getHeaders().getFirst("Authorization");

        // ❌ 4. BLOCK if no token
        if (header == null || !header.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = header.substring(7);

        try {
            String username = jwtUtil.getUsername(token);
            String role = jwtUtil.getRole(token);

            // ❌ 5. Validate token
            if (username == null || role == null || jwtUtil.isExpired(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // ✅ 6. Inject headers
            ServerHttpRequest mutatedRequest = cleanedRequest.mutate()
                    .header("X-User-Id", username)
                    .header("X-User-Role", role)
                    .header("X-Internal-Secret", "gateway-secret") // 🔥 ADD THIS
                    .build();

            // ✅ 7. Forward request
            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}