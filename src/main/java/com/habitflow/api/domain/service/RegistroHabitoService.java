package com.habitflow.api.domain.service;

import com.habitflow.api.domain.model.Racha;
import com.habitflow.api.domain.model.RegistroHabito;
import com.habitflow.api.domain.ports.input.RegistroHabitoUseCase;
import com.habitflow.api.domain.ports.output.RachaRepositoryPort;
import com.habitflow.api.domain.ports.output.RegistroHabitoRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class RegistroHabitoService implements RegistroHabitoUseCase {

    private final RegistroHabitoRepositoryPort registroRepositoryPort;
    private final RachaRepositoryPort rachaRepositoryPort;

    @Override
    public Mono<RegistroHabito> marcar(String habitoId, LocalDate fecha) {
        return registroRepositoryPort
                .findByHabitoIdAndFecha(habitoId, fecha)
                .flatMap(existente -> {
                    // Toggle: si ya existe, invertimos completado
                    existente.setCompletado(!existente.getCompletado());
                    return registroRepositoryPort.save(existente);
                })
                .switchIfEmpty(
                        // No existe: crear nuevo registro como completado
                        registroRepositoryPort.save(
                                RegistroHabito.builder()
                                        .habitoId(habitoId)
                                        .fecha(fecha)
                                        .completado(true)
                                        .createdAt(LocalDateTime.now())
                                        .build()
                        )
                )
                // Tras guardar el registro, recalcular racha
                .flatMap(registro ->
                        recalcularRacha(habitoId).thenReturn(registro)
                );
    }

    @Override
    public Flux<RegistroHabito> obtenerPorHabito(String habitoId) {
        return registroRepositoryPort.findByHabitoId(habitoId);
    }

    @Override
    public Flux<RegistroHabito> obtenerPorHabitoYRango(String habitoId, LocalDate desde, LocalDate hasta) {
        return registroRepositoryPort.findByHabitoIdAndFechaBetween(habitoId, desde, hasta);
    }

    @Override
    public Mono<RegistroHabito> obtenerPorHabitoYFecha(String habitoId, LocalDate fecha) {
        return registroRepositoryPort.findByHabitoIdAndFecha(habitoId, fecha)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("No hay registro para el hábito " + habitoId + " en la fecha " + fecha)
                ));
    }

    // ---------------------------------------------------------------
    // Lógica de racha
    // ---------------------------------------------------------------

    /**
     * Recalcula la racha actual y la mejor racha para un hábito.
     *
     * Algoritmo:
     *  1. Obtiene todos los registros completados, ordenados por fecha DESC.
     *  2. Cuenta días consecutivos desde el día más reciente hacia atrás.
     *  3. Actualiza (o crea) el documento Racha en la base de datos.
     */
    private Mono<Racha> recalcularRacha(String habitoId) {
        return registroRepositoryPort
                .findByHabitoId(habitoId)
                .filter(RegistroHabito::getCompletado)
                .map(RegistroHabito::getFecha)
                .collectSortedList((a, b) -> b.compareTo(a)) // orden DESC
                .flatMap(fechas -> {
                    int rachaActual = calcularRachaActual(fechas);
                    int mejorRacha  = calcularMejorRacha(fechas);
                    LocalDate ultimaFecha = fechas.isEmpty() ? null : fechas.get(0);

                    return rachaRepositoryPort
                            .findByHabitoId(habitoId)
                            .defaultIfEmpty(Racha.builder().habitoId(habitoId).mejorRacha(0).build())
                            .flatMap(racha -> {
                                racha.setRachaActual(rachaActual);
                                racha.setMejorRacha(Math.max(mejorRacha, racha.getMejorRacha()));
                                racha.setUltimaFecha(ultimaFecha);
                                return rachaRepositoryPort.save(racha);
                            });
                });
    }

    /**
     * Cuenta cuántos días consecutivos hay desde hoy (o ayer) hacia atrás.
     * Acepta que el último día sea hoy o ayer para no romper la racha
     * si el usuario aún no marcó el hábito del día.
     */
    private int calcularRachaActual(List<LocalDate> fechasDesc) {
        if (fechasDesc.isEmpty()) return 0;

        LocalDate referencia = LocalDate.now();
        LocalDate primero = fechasDesc.get(0);

        // Si el registro más reciente no es hoy ni ayer, la racha está rota
        if (primero.isBefore(referencia.minusDays(1))) return 0;

        int racha = 0;
        LocalDate esperada = primero;

        for (LocalDate fecha : fechasDesc) {
            if (fecha.equals(esperada)) {
                racha++;
                esperada = esperada.minusDays(1);
            } else {
                break;
            }
        }
        return racha;
    }

    /**
     * Recorre todas las fechas completadas y encuentra la racha más larga
     * de días consecutivos en toda la historia del hábito.
     */
    private int calcularMejorRacha(List<LocalDate> fechasDesc) {
        if (fechasDesc.isEmpty()) return 0;

        int mejor = 1;
        int actual = 1;

        for (int i = 1; i < fechasDesc.size(); i++) {
            LocalDate anterior = fechasDesc.get(i - 1);
            LocalDate current  = fechasDesc.get(i);

            if (anterior.minusDays(1).equals(current)) {
                actual++;
                mejor = Math.max(mejor, actual);
            } else {
                actual = 1;
            }
        }
        return mejor;
    }
}