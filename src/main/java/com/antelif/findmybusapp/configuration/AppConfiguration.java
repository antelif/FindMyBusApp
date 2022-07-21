package com.antelif.findmybusapp.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Application configuration. */
@Configuration
public class AppConfiguration {

  /** Returns a model mapper bean. */
  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
