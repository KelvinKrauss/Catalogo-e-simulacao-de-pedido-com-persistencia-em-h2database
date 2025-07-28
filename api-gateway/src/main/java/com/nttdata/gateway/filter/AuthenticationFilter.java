package com.nttdata.gateway.filter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {
    
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }
        
        if (!request.getHeaders().containsKey(AUTH_HEADER)) {
            return onError(exchange, "Token de autorização necessário", HttpStatus.UNAUTHORIZED);
        }
        
        String authHeader = request.getHeaders().getFirst(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return onError(exchange, "Formato de token inválido", HttpStatus.UNAUTHORIZED);
        }
        
        String token = authHeader.substring(BEARER_PREFIX.length());
        
        if (!"valid-token".equals(token)) {
            return onError(exchange, "Token inválido", HttpStatus.UNAUTHORIZED);
        }
        
        return chain.filter(exchange);
    }
    
    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }
    
    @Override
    public int getOrder() {
        return -1;
    }
}