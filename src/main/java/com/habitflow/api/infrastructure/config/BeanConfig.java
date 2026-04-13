package com.habitflow.api.infrastructure.config;

import com.habitflow.api.domain.ports.output.*;
import com.habitflow.api.domain.service.CategoriaService;
import com.habitflow.api.domain.service.HabitoService;
import com.habitflow.api.domain.service.RegistroHabitoService;
import com.habitflow.api.domain.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

  @Bean
  public CategoriaService categoriaService(
          CategoriaRepositoryPort categoriaRepositoryPort) {
    return new CategoriaService(categoriaRepositoryPort);
  }

  @Bean
  public UsuarioService usuarioService(
          UsuarioRepositoryPort usuarioRepositoryPort) {
    return new UsuarioService(usuarioRepositoryPort);
  }

  @Bean
  public HabitoService habitoService(
          HabitoRepositoryPort habitoRepositoryPort) {
    return new HabitoService(habitoRepositoryPort);
  }

  @Bean
  public RegistroHabitoService registroHabitoService(
          RegistroHabitoRepositoryPort registroHabitoRepositoryPort,
          RachaRepositoryPort rachaRepositoryPort) {
    return new RegistroHabitoService(registroHabitoRepositoryPort, rachaRepositoryPort);
  }
}
