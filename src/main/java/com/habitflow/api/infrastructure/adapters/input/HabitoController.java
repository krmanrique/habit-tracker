package com.habitflow.api.infrastructure.adapters.input;

import com.habitflow.api.domain.model.Habito;
import com.habitflow.api.domain.ports.input.HabitoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/habitos")
@RequiredArgsConstructor
@Tag(name = "Hábitos", description = "Gestión de hábitos personales")
public class HabitoController {

    private final HabitoUseCase habitoUseCase;

    @Operation(summary = "Crear un nuevo hábito")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Hábito creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Habito.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Habito> crear(@RequestBody Habito habito) {
        return habitoUseCase.crear(habito);
    }

    @Operation(summary = "Obtener todos los hábitos")
    @ApiResponse(responseCode = "200", description = "Lista de hábitos")
    @GetMapping
    public Flux<Habito> obtenerTodos() {
        return habitoUseCase.obtenerTodos();
    }

    @Operation(summary = "Obtener un hábito por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hábito encontrado",
                    content = @Content(schema = @Schema(implementation = Habito.class))),
            @ApiResponse(responseCode = "404", description = "Hábito no encontrado")
    })
    @GetMapping("/{id}")
    public Mono<Habito> obtenerPorId(
            @Parameter(description = "ID del hábito") @PathVariable String id) {
        return habitoUseCase.obtenerPorId(id);
    }

    @Operation(summary = "Obtener hábitos de un usuario")
    @ApiResponse(responseCode = "200", description = "Lista de hábitos del usuario")
    @GetMapping("/usuario/{usuarioId}")
    public Flux<Habito> obtenerPorUsuario(
            @Parameter(description = "ID del usuario") @PathVariable String usuarioId) {
        return habitoUseCase.obtenerPorUsuario(usuarioId);
    }

    @Operation(summary = "Obtener hábitos por categoría")
    @ApiResponse(responseCode = "200", description = "Lista de hábitos de la categoría")
    @GetMapping("/categoria/{categoriaId}")
    public Flux<Habito> obtenerPorCategoria(
            @Parameter(description = "ID de la categoría") @PathVariable String categoriaId) {
        return habitoUseCase.obtenerPorCategoria(categoriaId);
    }

    @Operation(summary = "Actualizar un hábito")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hábito actualizado",
                    content = @Content(schema = @Schema(implementation = Habito.class))),
            @ApiResponse(responseCode = "404", description = "Hábito no encontrado")
    })
    @PutMapping("/{id}")
    public Mono<Habito> actualizar(
            @Parameter(description = "ID del hábito") @PathVariable String id,
            @RequestBody Habito habito) {
        return habitoUseCase.actualizar(id, habito);
    }

    @Operation(summary = "Inactivar un hábito (soft delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Hábito inactivado"),
            @ApiResponse(responseCode = "404", description = "Hábito no encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> inactivar(
            @Parameter(description = "ID del hábito") @PathVariable String id) {
        return habitoUseCase.inactivar(id).then();
    }
}