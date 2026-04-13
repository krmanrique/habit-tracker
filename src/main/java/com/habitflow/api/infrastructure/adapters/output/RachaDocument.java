package com.habitflow.api.infrastructure.adapters.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rachas")
public class RachaDocument {
    @Id
    private String id;
    @Indexed(unique = true)
    private String habitoId;
    private Integer rachaActual;
    private Integer mejorRacha;
    private LocalDate ultimaFecha;
}