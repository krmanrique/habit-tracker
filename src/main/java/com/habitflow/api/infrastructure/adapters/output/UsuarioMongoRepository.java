package com.habitflow.api.infrastructure.adapters.output;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsuarioMongoRepository
        extends ReactiveMongoRepository<UsuarioDocument, String> {
    Mono<UsuarioDocument> findByEmail(String email);
}
