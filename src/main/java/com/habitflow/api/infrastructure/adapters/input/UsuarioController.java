package com.habitflow.api.infrastructure.adapters.input;

import com.habitflow.api.domain.model.AuthResponse;
import com.habitflow.api.domain.model.Usuario;
import com.habitflow.api.domain.ports.input.UsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Registro, autenticación y gestión de usuarios")
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;

    // ── Rutas públicas ────────────────────────────────────────────────────────

    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Usuario> registrar(@RequestBody Usuario usuario) {
        return usuarioUseCase.registrar(usuario);
    }

    @Operation(summary = "Iniciar sesión",
            description = "Retorna un token JWT válido por 24 horas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso — incluye el token JWT",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public Mono<AuthResponse> login(@RequestBody Map<String, String> credenciales) {
        return usuarioUseCase.login(
                credenciales.get("email"),
                credenciales.get("password")
        );
    }

    // ── Rutas protegidas (requieren Bearer token) ─────────────────────────────

    @Operation(summary = "Obtener todos los usuarios",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public Flux<Usuario> obtenerTodos() {
        return usuarioUseCase.obtenerTodos();
    }

    @Operation(summary = "Obtener usuario por ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public Mono<Usuario> obtenerPorId(
            @Parameter(description = "ID del usuario") @PathVariable String id) {
        return usuarioUseCase.obtenerPorId(id);
    }

    @Operation(summary = "Actualizar usuario",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public Mono<Usuario> actualizar(
            @Parameter(description = "ID del usuario") @PathVariable String id,
            @RequestBody Usuario usuario) {
        return usuarioUseCase.actualizar(id, usuario);
    }

    @Operation(summary = "Inactivar usuario",
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> inactivar(
            @Parameter(description = "ID del usuario") @PathVariable String id) {
        return usuarioUseCase.inactivar(id).then();
    }
}