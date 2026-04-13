package com.habitflow.api.infrastructure.adapters.output;

import com.habitflow.api.domain.model.Racha;
import com.habitflow.api.domain.ports.output.RachaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RachaRepositoryAdapter implements RachaRepositoryPort {

    private final RachaMongoRepository mongoRepository;

    @Override
    public Mono<Racha> save(Racha racha) {
        return mongoRepository.save(toDocument(racha)).map(this::toDomain);
    }

    @Override
    public Mono<Racha> findByHabitoId(String habitoId) {
        return mongoRepository.findByHabitoId(habitoId).map(this::toDomain);
    }

    private RachaDocument toDocument(Racha racha) {
        return RachaDocument.builder()
                .id(racha.getId())
                .habitoId(racha.getHabitoId())
                .rachaActual(racha.getRachaActual())
                .mejorRacha(racha.getMejorRacha())
                .ultimaFecha(racha.getUltimaFecha())
                .build();
    }

    private Racha toDomain(RachaDocument doc) {
        return Racha.builder()
                .id(doc.getId())
                .habitoId(doc.getHabitoId())
                .rachaActual(doc.getRachaActual())
                .mejorRacha(doc.getMejorRacha())
                .ultimaFecha(doc.getUltimaFecha())
                .build();
    }
}