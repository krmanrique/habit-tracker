package com.habitflow.api.infrastructure.adapters.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class UsuarioDocument {
    @Id
    private String id;
    private String nombre;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Boolean activo;
    private LocalDateTime createdAt;
}
