package com.antelif.findmybusapp.listener;

import static com.antelif.findmybusapp.domain.constant.Common.CONSUMER;

import com.antelif.findmybusapp.domain.BusLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/** Kafka listener. */
@Profile(CONSUMER)
@Service
@Slf4j
@RequiredArgsConstructor
public class BusLocationsListener {

  /**
   * Kafka listener for bus location updates.
   *
   * @param update the BusLocation object received from kafka topic.
   */
  @KafkaListener(topics = "${app.properties.kafka-default-topic}")
  public void busLocationsLister(BusLocation update) {

    log.info("Update received: {}", update);
  }
}
