package com.habitflow.api.domain.service;

import com.habitflow.api.domain.model.Categoria;
import com.habitflow.api.domain.ports.input.CategoriaUseCase;
import com.habitflow.api.domain.ports.output.CategoriaRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CategoriaService implements CategoriaUseCase {

  private final CategoriaRepositoryPort categoriaRepositoryPort;

  @Override
  public Mono<Categoria> crear(Categoria categoria) {
    categoria.setActivo(true);
    categoria.setCreatedAt(LocalDateTime.now());
    return categoriaRepositoryPort.save(categoria);
  }

  @Override
  public Mono<Categoria> actualizar(String id, Categoria categoria) {
    return categoriaRepositoryPort.findById(id)
            .flatMap(existing -> {
              existing.setNombre(categoria.getNombre());
              existing.setDescripcion(categoria.getDescripcion());
              return categoriaRepositoryPort.save(existing);
            });
  }

  @Override
  public Mono<Categoria> obtenerPorId(String id) {
    return categoriaRepositoryPort.findById(id);
  }

  @Override
  public Flux<Categoria> obtenerTodas() {
    return categoriaRepositoryPort.findAll();
  }

  @Override
  public Mono<Void> eliminar(String id) {
    return categoriaRepositoryPort.deleteById(id);
  }
}