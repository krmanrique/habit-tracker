package com.habitflow.api.infrastructure.config;

import com.habitflow.api.infrastructure.security.JwtAuthFilter;
import com.habitflow.api.infrastructure.security.JwtAuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthManager jwtAuthManager;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                // Deshabilitamos CSRF (API REST stateless)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                // Sin sesión HTTP — todo via JWT
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                // Manejamos el 401 y 403 con respuestas JSON limpias
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((exchange, e) ->
                                Mono.fromRunnable(() ->
                                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                        .accessDeniedHandler((exchange, e) ->
                                Mono.fromRunnable(() ->
                                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                )
                // ── Rutas públicas ──────────────────────────────────────────
                .authorizeExchange(auth -> auth
                        // Registro y login sin token
                        .pathMatchers(HttpMethod.POST,
                                "/api/usuarios/registro",
                                "/api/usuarios/login").permitAll()
                        // Swagger / OpenAPI sin token
                        .pathMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/webjars/**").permitAll()
                        // Todo lo demás requiere autenticación
                        .anyExchange().authenticated()
                )
                // Registramos nuestro filtro JWT antes del filtro de autenticación
                .addFilterBefore(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}