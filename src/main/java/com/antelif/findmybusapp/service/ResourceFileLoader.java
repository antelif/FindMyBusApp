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

      var customException = new InvalidFileException(FILE_NOT_FOUND);
      ErrorHandler.printError(customException);
      throw customException;
    }
    this.busLocations = busLinesOfProducer(busLocationsList);
  }

  /**
   * Creates a list of bus locations updates for this producer based on the updates provided from
   * the bus location source file and the producer id that is configured.
   *
   * @param updates the bus locations as read and retrieved for the bus locations source file.
   * @return a list of bus locations fro this producer. It should be a sub list of the updates
   *     provided in parameters.
   */
  private LinkedList<BusLocation> busLinesOfProducer(LinkedList<BusLocation> updates) {
    var totalProducers = appProperties.getKafkaNumberOfProducers();
    var producerId = appProperties.getKafkaProducerId();
    var busLinesByIds = updates.stream().collect(groupingBy(BusLocation::getBusId));

    // Create as many lists as producers. This should be a list containing sub lists.
    var busLinesPerProducer = new ArrayList<List<BusLocation>>(totalProducers);
    for (int i = 0; i < totalProducers; i++) {
      busLinesPerProducer.add(new ArrayList<>());
    }

    // Assign bus lines for each producer and get the list of lines of the producer for this
    // producer id configured.
    var iterator = busLinesByIds.values().iterator();
    for (int i = 0; iterator.hasNext(); i++) {
      busLinesPerProducer.get(i % totalProducers).addAll(iterator.next());
    }
    return new LinkedList<>(busLinesPerProducer.get(producerId));
  }
}
