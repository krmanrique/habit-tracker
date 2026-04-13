package com.habitflow.api.infrastructure.adapters.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "categorias")
public class CategoriaDocument {
  @Id
  private String id;
  private String nombre;
  private String descripcion;
  private Boolean activo;
  private LocalDateTime createdAt;
}