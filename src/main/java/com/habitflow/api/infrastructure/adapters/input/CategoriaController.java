package com.habitflow.api.infrastructure.adapters.input;

import com.habitflow.api.domain.model.Categoria;
import com.habitflow.api.domain.ports.input.CategoriaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

  private final CategoriaUseCase categoriaUseCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Categoria> crear(@RequestBody Categoria categoria) {
    return categoriaUseCase.crear(categoria);
  }

  @GetMapping
  public Flux<Categoria> obtenerTodas() {
    return categoriaUseCase.obtenerTodas();
  }

  @GetMapping("/{id}")
  public Mono<Categoria> obtenerPorId(@PathVariable String id) {
    return categoriaUseCase.obtenerPorId(id);
  }

  @PutMapping("/{id}")
  public Mono<Categoria> actualizar(@PathVariable String id,
                                    @RequestBody Categoria categoria) {
    return categoriaUseCase.actualizar(id, categoria);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> eliminar(@PathVariable String id) {
    return categoriaUseCase.eliminar(id);
  }
}