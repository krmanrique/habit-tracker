package com.habitflow.api.infrastructure.adapters.output;

import com.habitflow.api.domain.model.RegistroHabito;
import com.habitflow.api.domain.ports.output.RegistroHabitoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class RegistroHabitoRepositoryAdapter implements RegistroHabitoRepositoryPort {

    private final RegistroHabitoMongoRepository mongoRepository;

    @Override
    public Mono<RegistroHabito> save(RegistroHabito registro) {
        return mongoRepository.save(toDocument(registro)).map(this::toDomain);
    }

    @Override
    public Mono<RegistroHabito> findByHabitoIdAndFecha(String habitoId, LocalDate fecha) {
        return mongoRepository.findByHabitoIdAndFecha(habitoId, fecha).map(this::toDomain);
    }

    @Override
    public Flux<RegistroHabito> findByHabitoId(String habitoId) {
        return mongoRepository.findByHabitoId(habitoId).map(this::toDomain);
    }

    @Override
    public Flux<RegistroHabito> findByHabitoIdAndFechaBetween(String habitoId, LocalDate desde, LocalDate hasta) {
        return mongoRepository.findByHabitoIdAndFechaBetween(habitoId, desde, hasta).map(this::toDomain);
    }

    private RegistroHabitoDocument toDocument(RegistroHabito registro) {
        return RegistroHabitoDocument.builder()
                .id(registro.getId())
                .habitoId(registro.getHabitoId())
                .fecha(registro.getFecha())
                .completado(registro.getCompletado())
                .createdAt(registro.getCreatedAt())
                .build();
    }

    private RegistroHabito toDomain(RegistroHabitoDocument doc) {
        return RegistroHabito.builder()
                .id(doc.getId())
                .habitoId(doc.getHabitoId())
                .fecha(doc.getFecha())
                .completado(doc.getCompletado())
                .createdAt(doc.getCreatedAt())
                .build();
    }
}