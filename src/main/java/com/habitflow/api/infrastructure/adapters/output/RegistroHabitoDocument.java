package com.habitflow.api.infrastructure.adapters.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "registros_habito")
@CompoundIndex(name = "habito_fecha_idx", def = "{'habitoId': 1, 'fecha': 1}", unique = true)
public class RegistroHabitoDocument {
    @Id
    private String id;
    private String habitoId;
    private LocalDate fecha;
    private Boolean completado;
    private LocalDateTime createdAt;
}