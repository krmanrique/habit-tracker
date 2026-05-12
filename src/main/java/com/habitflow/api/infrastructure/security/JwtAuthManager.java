package com.habitflow.api.infrastructure.security;

import com.habitflow.api.infrastructure.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        if (!jwtUtil.esValido(token)) {
            return Mono.error(new RuntimeException("Token JWT inválido o expirado"));
        }

        String email = jwtUtil.extraerEmail(token);
        String usuarioId = jwtUtil.extraerUsuarioId(token);

        // Construimos el Authentication con email como principal
        // y usuarioId como detalle para poder recuperarlo desde controllers
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
        auth.setDetails(usuarioId);

        return Mono.just(auth);
    }
}