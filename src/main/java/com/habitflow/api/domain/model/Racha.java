package com.habitflow.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Racha {
    private String id;
    private String habitoId;
    private Integer rachaActual;
    private Integer mejorRacha;
    private LocalDate ultimaFecha;
}