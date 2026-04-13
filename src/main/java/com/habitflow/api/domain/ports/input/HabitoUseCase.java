package com.habitflow.api.domain.ports.input;

import com.habitflow.api.domain.model.Habito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HabitoUseCase {
    Mono<Habito> crear(Habito habito);
    Mono<Habito> actualizar(String id, Habito habito);
    Mono<Habito> inactivar(String id);
    Mono<Habito> obtenerPorId(String id);
    Flux<Habito> obtenerTodos();
    Flux<Habito> obtenerPorUsuario(String usuarioId);
    Flux<Habito> obtenerPorCategoria(String categoriaId);
}