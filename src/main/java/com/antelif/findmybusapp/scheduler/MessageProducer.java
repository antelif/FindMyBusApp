package com.antelif.findmybusapp.scheduler;

import static com.antelif.findmybusapp.domain.constant.Common.PRODUCER;

import com.antelif.findmybusapp.configuration.AppProperties;
import com.antelif.findmybusapp.domain.BusLocation;
import com.antelif.findmybusapp.service.BusLocationUpdatesService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/** Kafka producer. */
@Profile(PRODUCER)
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

  private final KafkaTemplate<String, BusLocation> kafkaTemplate;
  private final BusLocationUpdatesService busLocationUpdatesService;
  private final AppProperties appProperties;

  /** Scheduled kafka producer that sends a BusLocation update every X milliseconds. */
  @Scheduled(fixedRateString = "${app.properties.publisher-rate-millis}")
//  @Async
  public void publishMessage() {

    if (busLocationUpdatesService.hasUpdates()) {
      var message = busLocationUpdatesService.getNextUpdate();
      message.setTime(Instant.now().toString());

      log.info("Sending update...{}", message);

      kafkaTemplate.send(appProperties.getKafkaDefaultTopic(), message);
    }
  }
}
