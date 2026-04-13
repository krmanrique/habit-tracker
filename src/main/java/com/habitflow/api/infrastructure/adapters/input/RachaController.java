package com.habitflow.api.infrastructure.adapters.input;

import com.habitflow.api.domain.model.Racha;
import com.habitflow.api.domain.ports.output.RachaRepositoryPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/rachas")
@RequiredArgsConstructor
@Tag(name = "Rachas", description = "Consulta de rachas de hábitos")
public class RachaController {

    private final RachaRepositoryPort rachaRepositoryPort;

    @Operation(summary = "Obtener la racha actual de un hábito")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Racha encontrada",
                    content = @Content(schema = @Schema(implementation = Racha.class))),
            @ApiResponse(responseCode = "404", description = "No hay racha registrada para este hábito")
    })
    @GetMapping("/habito/{habitoId}")
    public Mono<Racha> obtenerPorHabito(
            @Parameter(description = "ID del hábito") @PathVariable String habitoId) {
        return rachaRepositoryPort.findByHabitoId(habitoId)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("No hay racha registrada para el hábito: " + habitoId)
                ));
    }
}