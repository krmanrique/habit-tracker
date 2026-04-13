package com.habitflow.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
  private String id;
  private String nombre;
  private String descripcion;
  private Boolean activo;
  private LocalDateTime createdAt;
}