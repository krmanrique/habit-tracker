package com.habitflow.api.domain.ports.output;

import com.habitflow.api.domain.model.Racha;
import reactor.core.publisher.Mono;

public interface RachaRepositoryPort {
    Mono<Racha> save(Racha racha);
    Mono<Racha> findByHabitoId(String habitoId);
}