package com.antelif.findmybusapp.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** Application properties as defined in application yaml file. */
@ConfigurationProperties(value = "app.properties")
@Component
@Getter
@Setter
public class AppProperties {
  private String busLocationSourceFile;
  private String kafkaDefaultTopic;
  private Integer kafkaNumberOfProducers;
  private Integer kafkaProducerId;
}
