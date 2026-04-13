package com.habitflow.api.infrastructure.config;

import com.habitflow.api.domain.ports.output.CategoriaRepositoryPort;
import com.habitflow.api.domain.service.CategoriaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

  @Bean
  public CategoriaService categoriaService(CategoriaRepositoryPort categoriaRepositoryPort) {
    return new CategoriaService(categoriaRepositoryPort);
  }
}