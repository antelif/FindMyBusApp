package com.antelif.findmybusapp.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Scheduled(fixedRateString = "${app.properties.publisher-rate-millis}")
  public void publishMessage() {
    var message = "Message "+ Instant.now().truncatedTo(ChronoUnit.DAYS).toString();

    kafkaTemplate.send("bus.locations",message);
  }
}
