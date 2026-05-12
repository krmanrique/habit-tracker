package com.habitflow.api.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements WebFilter {

    private final JwtAuthManager jwtAuthManager;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // Si no hay header o no empieza con "Bearer ", dejamos pasar sin autenticar
        // (SecurityConfig decidirá si el endpoint requiere auth o no)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7);

        // Creamos un Authentication temporal con el token como credencial
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(token, token);

        return jwtAuthManager.authenticate(authRequest)
                .flatMap(auth ->
                        chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
                )
                .onErrorResume(e -> chain.filter(exchange));
        // onErrorResume: si el token es inválido dejamos pasar;
        // SecurityConfig bloqueará el acceso a rutas protegidas
    }
}