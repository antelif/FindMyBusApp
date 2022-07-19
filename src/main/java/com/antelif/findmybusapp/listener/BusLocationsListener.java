package com.antelif.findmybusapp.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BusLocationsListener {

  @KafkaListener(topics ="${spring.kafka.template.default-topic}")
  public void busLocationsLister(String message) {

    log.info("This is the received message: {}", message);
  }
}
