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
public class Habito {
    private String id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private Boolean activo;
    private String usuarioId;
    private String categoriaId;
    private LocalDateTime createdAt;
}