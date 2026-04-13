package com.habitflow.api.infrastructure.adapters.output;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface RegistroHabitoMongoRepository
        extends ReactiveMongoRepository<RegistroHabitoDocument, String> {

    Mono<RegistroHabitoDocument> findByHabitoIdAndFecha(String habitoId, LocalDate fecha);

    Flux<RegistroHabitoDocument> findByHabitoId(String habitoId);

    Flux<RegistroHabitoDocument> findByHabitoIdAndFechaBetween(String habitoId, LocalDate desde, LocalDate hasta);
}