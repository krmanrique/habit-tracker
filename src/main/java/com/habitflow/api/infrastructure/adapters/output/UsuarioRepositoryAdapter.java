package com.habitflow.api.infrastructure.adapters.output;

import com.habitflow.api.domain.model.Usuario;
import com.habitflow.api.domain.ports.output.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioMongoRepository mongoRepository;

    @Override
    public Mono<Usuario> save(Usuario usuario) {
        return mongoRepository.save(toDocument(usuario)).map(this::toDomain);
    }

    @Override
    public Mono<Usuario> findById(String id) {
        return mongoRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Mono<Usuario> findByEmail(String email) {
        return mongoRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Flux<Usuario> findAll() {
        return mongoRepository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return mongoRepository.existsById(email);
    }

    private UsuarioDocument toDocument(Usuario usuario) {
        return UsuarioDocument.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .activo(usuario.getActivo())
                .createdAt(usuario.getCreatedAt())
                .build();
    }

    private Usuario toDomain(UsuarioDocument doc) {
        return Usuario.builder()
                .id(doc.getId())
                .nombre(doc.getNombre())
                .email(doc.getEmail())
                .password(doc.getPassword())
                .activo(doc.getActivo())
                .createdAt(doc.getCreatedAt())
                .build();
    }
}
