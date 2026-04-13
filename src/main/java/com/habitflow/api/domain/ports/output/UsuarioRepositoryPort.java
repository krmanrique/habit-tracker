package com.habitflow.api.domain.ports.output;

import com.habitflow.api.domain.model.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsuarioRepositoryPort {
    Mono<Usuario> save(Usuario usuario);
    Mono<Usuario> findById(String id);
    Mono<Usuario> findByEmail(String email);
    Flux<Usuario> findAll();
    Mono<Boolean> existsByEmail(String email);
}
