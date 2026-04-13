package com.habitflow.api.infrastructure.adapters.output;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CategoriaMongoRepository
        extends ReactiveMongoRepository<CategoriaDocument, String> {

  Flux<CategoriaDocument> findByActivoTrue();
}