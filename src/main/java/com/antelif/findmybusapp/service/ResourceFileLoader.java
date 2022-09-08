package com.antelif.findmybusapp.service;

import static com.antelif.findmybusapp.domain.constant.Common.PRODUCER;
import static com.antelif.findmybusapp.domain.error.GenericError.FILE_NOT_FOUND;
import static java.util.stream.Collectors.groupingBy;

import com.antelif.findmybusapp.configuration.AppProperties;
import com.antelif.findmybusapp.domain.BusLocation;
import com.antelif.findmybusapp.domain.exception.InvalidFileException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Profile(PRODUCER)
@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class ResourceFileLoader {
  private final AppProperties appProperties;

  protected LinkedList<BusLocation> busLocations = new LinkedList<>();

  /**
   * Reads bus location updates from resource file and creates a list with bus locations to provide.
   */
  public void generateBusLocations() {
    var busLocationsList = new LinkedList<BusLocation>();
    try {

      // TODO: Read just a few of total lines depending on producer id.

      // Load file and create scanner to read it.
      var busLocationsFile = ResourceUtils.getFile(appProperties.getBusLocationSourceFile());
      var scanner = new Scanner(busLocationsFile);

      // Create a BusLocation object from each line read.
      while (scanner.hasNextLine()) {
        var line = scanner.nextLine();
        var busLocation = new BusLocation(line);
        busLocationsList.add(busLocation);
      }
      scanner.close();

    } catch (FileNotFoundException exception) {
//      log.error("File {} not found: ", appProperties.getBusLocationSourceFile());
      throw new InvalidFileException(FILE_NOT_FOUND);
    }
    this.busLocations = busLinesOfProducer(busLocationsList);
  }

  private LinkedList<BusLocation> busLinesOfProducer(LinkedList<BusLocation> updates) {
    var totalProducers = appProperties.getKafkaNumberOfProducers();
    var producerId = appProperties.getKafkaProducerId();
    var busLinesByIds = updates.stream().collect(groupingBy(BusLocation::getBusId));

    // Create as many lists as needed
    var busLinesPerProducer = new ArrayList<List<BusLocation>>(totalProducers);
    for (int i = 0; i < totalProducers; i++) {
      busLinesPerProducer.add(new ArrayList<>());
    }

    var iterator = busLinesByIds.values().iterator();
    for (int i = 0; iterator.hasNext(); i++) {
      busLinesPerProducer.get(i % totalProducers).addAll(iterator.next());
    }
    return new LinkedList<>(busLinesPerProducer.get(producerId));
  }
}
