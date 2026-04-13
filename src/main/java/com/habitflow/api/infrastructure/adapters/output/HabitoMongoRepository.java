package com.habitflow.api.infrastructure.adapters.output;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface HabitoMongoRepository extends ReactiveMongoRepository<HabitoDocument, String> {
    Flux<HabitoDocument> findByUsuarioId(String usuarioId);
    Flux<HabitoDocument> findByCategoriaId(String categoriaId);
}