package com.habitflow.api.domain.ports.output;

import com.habitflow.api.domain.model.Habito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HabitoRepositoryPort {
    Mono<Habito> save(Habito habito);
    Mono<Habito> findById(String id);
    Flux<Habito> findAll();
    Flux<Habito> findByUsuarioId(String usuarioId);
    Flux<Habito> findByCategoriaId(String categoriaId);
    Mono<Void> deleteById(String id);
}