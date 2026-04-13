package com.habitflow.api.infrastructure.adapters.input;

import com.habitflow.api.domain.model.Usuario;
import com.habitflow.api.domain.ports.input.UsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Usuario> registrar(@RequestBody Usuario usuario) {
        return usuarioUseCase.registrar(usuario);
    }

    @PostMapping("/login")
    public Mono<Usuario> login(@RequestBody Map<String, String> credenciales) {
        return usuarioUseCase.login(
                credenciales.get("email"),
                credenciales.get("password")
        );
    }

    @GetMapping
    public Flux<Usuario> obtenerTodos() {
        return usuarioUseCase.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Mono<Usuario> obtenerPorId(@PathVariable String id) {
        return usuarioUseCase.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Mono<Usuario> actualizar(@PathVariable String id,
                                    @RequestBody Usuario usuario) {
        return usuarioUseCase.actualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> inactivar(@PathVariable String id) {
        return usuarioUseCase.inactivar(id).then();
    }
}
