package com.habitflow.api.domain.ports.input;

import com.habitflow.api.domain.model.Categoria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoriaUseCase {
  Mono<Categoria> crear(Categoria categoria);
  Mono<Categoria> actualizar(String id, Categoria categoria);
  Mono<Categoria> obtenerPorId(String id);
  Flux<Categoria> obtenerTodas();
  Mono<Void> eliminar(String id);
}