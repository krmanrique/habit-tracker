package com.habitflow.api.infrastructure.adapters.output;

import com.habitflow.api.domain.model.Habito;
import com.habitflow.api.domain.ports.output.HabitoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HabitoRepositoryAdapter implements HabitoRepositoryPort {

    private final HabitoMongoRepository mongoRepository;

    @Override
    public Mono<Habito> save(Habito habito) {
        return mongoRepository.save(toDocument(habito)).map(this::toDomain);
    }

    @Override
    public Mono<Habito> findById(String id) {
        return mongoRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<Habito> findAll() {
        return mongoRepository.findAll().map(this::toDomain);
    }

    @Override
    public Flux<Habito> findByUsuarioId(String usuarioId) {
        return mongoRepository.findByUsuarioId(usuarioId).map(this::toDomain);
    }

    @Override
    public Flux<Habito> findByCategoriaId(String categoriaId) {
        return mongoRepository.findByCategoriaId(categoriaId).map(this::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return mongoRepository.deleteById(id);
    }

    private HabitoDocument toDocument(Habito habito) {
        return HabitoDocument.builder()
                .id(habito.getId())
                .nombre(habito.getNombre())
                .descripcion(habito.getDescripcion())
                .fechaInicio(habito.getFechaInicio())
                .activo(habito.getActivo())
                .usuarioId(habito.getUsuarioId())
                .categoriaId(habito.getCategoriaId())
                .createdAt(habito.getCreatedAt())
                .build();
    }

    private Habito toDomain(HabitoDocument doc) {
        return Habito.builder()
                .id(doc.getId())
                .nombre(doc.getNombre())
                .descripcion(doc.getDescripcion())
                .fechaInicio(doc.getFechaInicio())
                .activo(doc.getActivo())
                .usuarioId(doc.getUsuarioId())
                .categoriaId(doc.getCategoriaId())
                .createdAt(doc.getCreatedAt())
                .build();
    }
}