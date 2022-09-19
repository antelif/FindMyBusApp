package com.antelif.findmybusapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.antelif.findmybusapp.BaseIntegrationTest;
import com.antelif.findmybusapp.configuration.AppProperties;
import com.antelif.findmybusapp.domain.BusLocation;
import com.antelif.findmybusapp.domain.exception.GenericException;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
public class ResourceFileLoaderTest extends BaseIntegrationTest {

  @Autowired private AppProperties appProperties;
  @Autowired private ResourceFileLoader resourceFileLoader;

  @Test
  @DisplayName("One producer should get two lines")
  @DirtiesContext
  void oneProducerGetsThreeLines() {

    // Change to 1 producer
    appProperties.setKafkaNumberOfProducers(1);

    // Assign bus lines
    resourceFileLoader.generateBusLocations();

    var producerAssignedBusLocations = resourceFileLoader.getBusLocations();
    var producersAssignedBusLines =
        producerAssignedBusLocations.stream()
            .map(BusLocation::getBusId)
            .collect(Collectors.toSet());

    assertEquals(3, producersAssignedBusLines.size());
  }

  @Test
  @DisplayName("Two producers share three lines")
  void twoProducersShareThreeLines() {

    // Change to 1 producer
    appProperties.setKafkaNumberOfProducers(2);

    // Assign bus lines for first producer
    appProperties.setKafkaProducerId(0);
    resourceFileLoader.generateBusLocations();

    var firstProducerAssignedBusLocations = resourceFileLoader.getBusLocations();
    var firstProducersAssignedBusLines =
        firstProducerAssignedBusLocations.stream()
            .map(BusLocation::getBusId)
            .collect(Collectors.toSet());
    // First producer should have two bus lines
    assertEquals(2, firstProducersAssignedBusLines.size());

    // Assign bus lines for second producer
    appProperties.setKafkaProducerId(1);
    resourceFileLoader.generateBusLocations();

    var secondProducerAssignedBusLocations = resourceFileLoader.getBusLocations();
    var secondProducersAssignedBusLines =
        secondProducerAssignedBusLocations.stream()
            .map(BusLocation::getBusId)
            .collect(Collectors.toSet());

    // Second producer should have one line
    assertEquals(1, secondProducersAssignedBusLines.size());
  }

  @Test
  @DisplayName("Four producers share three lines")
  void fourProducersShareThreeLines() {
    // Change to 1 producer
    appProperties.setKafkaNumberOfProducers(4);

    // Assign bus lines for forth producer
    appProperties.setKafkaProducerId(3);
    resourceFileLoader.generateBusLocations();

    var forthProducerAssignedBusLocations = resourceFileLoader.getBusLocations();
    var forthProducersAssignedBusLines =
        forthProducerAssignedBusLocations.stream()
            .map(BusLocation::getBusId)
            .collect(Collectors.toSet());
    // First producer should have two bus lines
    assertEquals(0, forthProducersAssignedBusLines.size());
  }

  @Test
  @DirtiesContext
  @DisplayName("File not found throws correct exception")
  void fileNotFoundThrowsException() {
    appProperties.setBusLocationSourceFile("");
    var exception =
        assertThrows(GenericException.class, () -> resourceFileLoader.generateBusLocations());
    assertEquals(2, exception.getGenericError().getErrorCode());
  }
}
