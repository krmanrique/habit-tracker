package com.habitflow.api.domain.ports.output;

import com.habitflow.api.domain.model.RegistroHabito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface RegistroHabitoRepositoryPort {
    Mono<RegistroHabito> save(RegistroHabito registro);
    Mono<RegistroHabito> findByHabitoIdAndFecha(String habitoId, LocalDate fecha);
    Flux<RegistroHabito> findByHabitoId(String habitoId);
    Flux<RegistroHabito> findByHabitoIdAndFechaBetween(String habitoId, LocalDate desde, LocalDate hasta);
}