package com.habitflow.api.domain.ports.input;

import com.habitflow.api.domain.model.AuthResponse;
import com.habitflow.api.domain.model.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UsuarioUseCase {
    Mono<Usuario> registrar(Usuario usuario);
    Mono<AuthResponse> login(String email, String password);  // ahora retorna AuthResponse
    Mono<Usuario> actualizar(String id, Usuario usuario);
    Mono<Usuario> inactivar(String id);
    Flux<Usuario> obtenerTodos();
    Mono<Usuario> obtenerPorId(String id);
}