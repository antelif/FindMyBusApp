package com.antelif.findmybusapp.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(value = "app.properties")
@Getter
@Setter
public class AppProperties {
  private Long publisherRateSeconds;
  private String kafkaTopic;
}
