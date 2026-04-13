package com.habitflow.api.domain.service;

import com.habitflow.api.domain.model.Usuario;
import com.habitflow.api.domain.ports.input.UsuarioUseCase;
import com.habitflow.api.domain.ports.output.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UsuarioService implements UsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    @Override
    public Mono<Usuario> registrar(Usuario usuario) {
        usuario.setActivo(true);
        usuario.setCreatedAt(LocalDateTime.now());
        return usuarioRepositoryPort.save(usuario);
    }

    @Override
    public Mono<Usuario> login(String email, String password) {
        return usuarioRepositoryPort.findByEmail(email)
                .filter(u -> u.getPassword().equals(password))
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Credenciales incorrectas")));
    }

    @Override
    public Mono<Usuario> actualizar(String id, Usuario usuario) {
        return usuarioRepositoryPort.findById(id)
                .flatMap(existing -> {
                    existing.setNombre(usuario.getNombre());
                    existing.setEmail(usuario.getEmail());
                    return usuarioRepositoryPort.save(existing);
                });
    }

    @Override
    public Mono<Usuario> inactivar(String id) {
        return usuarioRepositoryPort.findById(id)
                .flatMap(existing -> {
                    existing.setActivo(false);
                    return usuarioRepositoryPort.save(existing);
                });
    }

    @Override
    public Flux<Usuario> obtenerTodos() {
        return usuarioRepositoryPort.findAll();
    }

    @Override
    public Mono<Usuario> obtenerPorId(String id) {
        return usuarioRepositoryPort.findById(id);
    }
}
