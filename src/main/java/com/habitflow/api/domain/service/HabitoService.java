package com.habitflow.api.domain.service;

import com.habitflow.api.domain.model.Habito;
import com.habitflow.api.domain.ports.input.HabitoUseCase;
import com.habitflow.api.domain.ports.output.HabitoRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class HabitoService implements HabitoUseCase {

    private final HabitoRepositoryPort habitoRepositoryPort;

    @Override
    public Mono<Habito> crear(Habito habito) {
        habito.setActivo(true);
        habito.setFechaInicio(LocalDate.now());
        habito.setCreatedAt(LocalDateTime.now());
        return habitoRepositoryPort.save(habito);
    }

    @Override
    public Mono<Habito> actualizar(String id, Habito habito) {
        return habitoRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Hábito no encontrado con id: " + id)))
                .flatMap(existing -> {
                    existing.setNombre(habito.getNombre());
                    existing.setDescripcion(habito.getDescripcion());
                    existing.setCategoriaId(habito.getCategoriaId());
                    return habitoRepositoryPort.save(existing);
                });
    }

    @Override
    public Mono<Habito> inactivar(String id) {
        return habitoRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Hábito no encontrado con id: " + id)))
                .flatMap(existing -> {
                    existing.setActivo(false);
                    return habitoRepositoryPort.save(existing);
                });
    }

    @Override
    public Mono<Habito> obtenerPorId(String id) {
        return habitoRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Hábito no encontrado con id: " + id)));
    }

    @Override
    public Flux<Habito> obtenerTodos() {
        return habitoRepositoryPort.findAll();
    }

    @Override
    public Flux<Habito> obtenerPorUsuario(String usuarioId) {
        return habitoRepositoryPort.findByUsuarioId(usuarioId);
    }

    @Override
    public Flux<Habito> obtenerPorCategoria(String categoriaId) {
        return habitoRepositoryPort.findByCategoriaId(categoriaId);
    }
}