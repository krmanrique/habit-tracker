package com.habitflow.api.domain.ports.output;

import com.habitflow.api.domain.model.Categoria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoriaRepositoryPort {
  Mono<Categoria> save(Categoria categoria);
  Mono<Categoria> findById(String id);
  Flux<Categoria> findAll();
  Mono<Void> deleteById(String id);
  Mono<Boolean> existsById(String id);
}