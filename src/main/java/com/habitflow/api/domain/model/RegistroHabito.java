package com.habitflow.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroHabito {
    private String id;
    private String habitoId;
    private LocalDate fecha;
    private Boolean completado;
    private LocalDateTime createdAt;
}