package com.habitflow.api.infrastructure.adapters.input;

import com.habitflow.api.domain.model.RegistroHabito;
import com.habitflow.api.domain.ports.input.RegistroHabitoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
@Tag(name = "Registros de Hábito", description = "Marcar y consultar el cumplimiento diario de hábitos")
public class RegistroHabitoController {

    private final RegistroHabitoUseCase registroHabitoUseCase;

    @Operation(
            summary = "Marcar / desmarcar un hábito",
            description = "Si ya existe un registro para ese hábito en esa fecha, hace toggle de 'completado'. "
                    + "Si no existe, crea uno nuevo como completado. Recalcula la racha automáticamente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro actualizado o creado",
                    content = @Content(schema = @Schema(implementation = RegistroHabito.class))),
            @ApiResponse(responseCode = "404", description = "Hábito no encontrado")
    })
    @PostMapping("/marcar/{habitoId}")
    public Mono<RegistroHabito> marcar(
            @Parameter(description = "ID del hábito a marcar") @PathVariable String habitoId,
            @Parameter(description = "Fecha del registro (por defecto hoy). Formato: yyyy-MM-dd")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        LocalDate fechaRegistro = (fecha != null) ? fecha : LocalDate.now();
        return registroHabitoUseCase.marcar(habitoId, fechaRegistro);
    }

    @Operation(summary = "Obtener todos los registros de un hábito")
    @ApiResponse(responseCode = "200", description = "Lista de registros")
    @GetMapping("/habito/{habitoId}")
    public Flux<RegistroHabito> obtenerPorHabito(
            @Parameter(description = "ID del hábito") @PathVariable String habitoId) {
        return registroHabitoUseCase.obtenerPorHabito(habitoId);
    }

    @Operation(summary = "Obtener registros de un hábito en un rango de fechas")
    @ApiResponse(responseCode = "200", description = "Lista de registros en el rango")
    @GetMapping("/habito/{habitoId}/rango")
    public Flux<RegistroHabito> obtenerPorRango(
            @Parameter(description = "ID del hábito") @PathVariable String habitoId,
            @Parameter(description = "Fecha inicio (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @Parameter(description = "Fecha fin (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return registroHabitoUseCase.obtenerPorHabitoYRango(habitoId, desde, hasta);
    }

    @Operation(summary = "Obtener el registro de un hábito para una fecha específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado",
                    content = @Content(schema = @Schema(implementation = RegistroHabito.class))),
            @ApiResponse(responseCode = "404", description = "No hay registro para esa fecha")
    })
    @GetMapping("/habito/{habitoId}/fecha")
    public Mono<RegistroHabito> obtenerPorFecha(
            @Parameter(description = "ID del hábito") @PathVariable String habitoId,
            @Parameter(description = "Fecha a consultar (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return registroHabitoUseCase.obtenerPorHabitoYFecha(habitoId, fecha);
    }
}