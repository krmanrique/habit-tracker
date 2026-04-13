package com.habitflow.api.domain.ports.input;

import com.habitflow.api.domain.model.RegistroHabito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface RegistroHabitoUseCase {
    /**
     * Marca o desmarca un hábito para una fecha dada.
     * Si ya existe un registro ese día, hace toggle del campo completado.
     * Si no existe, crea uno nuevo con completado = true.
     * En ambos casos recalcula la racha del hábito.
     */
    Mono<RegistroHabito> marcar(String habitoId, LocalDate fecha);

    Flux<RegistroHabito> obtenerPorHabito(String habitoId);

    Flux<RegistroHabito> obtenerPorHabitoYRango(String habitoId, LocalDate desde, LocalDate hasta);

    Mono<RegistroHabito> obtenerPorHabitoYFecha(String habitoId, LocalDate fecha);
}