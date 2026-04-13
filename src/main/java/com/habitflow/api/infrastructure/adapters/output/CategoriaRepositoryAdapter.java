package com.habitflow.api.infrastructure.adapters.output;

import com.habitflow.api.domain.model.Categoria;
import com.habitflow.api.domain.ports.output.CategoriaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CategoriaRepositoryAdapter implements CategoriaRepositoryPort {

  private final CategoriaMongoRepository mongoRepository;

  @Override
  public Mono<Categoria> save(Categoria categoria) {
    CategoriaDocument doc = toDocument(categoria);
    return mongoRepository.save(doc).map(this::toDomain);
  }

  @Override
  public Mono<Categoria> findById(String id) {
    return mongoRepository.findById(id).map(this::toDomain);
  }

  @Override
  public Flux<Categoria> findAll() {
    return mongoRepository.findAll().map(this::toDomain);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return mongoRepository.deleteById(id);
  }

  @Override
  public Mono<Boolean> existsById(String id) {
    return mongoRepository.existsById(id);
  }

  private CategoriaDocument toDocument(Categoria categoria) {
    return CategoriaDocument.builder()
            .id(categoria.getId())
            .nombre(categoria.getNombre())
            .descripcion(categoria.getDescripcion())
            .activo(categoria.getActivo())
            .createdAt(categoria.getCreatedAt())
            .build();
  }

  private Categoria toDomain(CategoriaDocument doc) {
    return Categoria.builder()
            .id(doc.getId())
            .nombre(doc.getNombre())
            .descripcion(doc.getDescripcion())
            .activo(doc.getActivo())
            .createdAt(doc.getCreatedAt())
            .build();
  }
}