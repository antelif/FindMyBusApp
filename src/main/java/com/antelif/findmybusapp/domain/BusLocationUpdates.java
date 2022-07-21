package com.antelif.findmybusapp.domain;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

/**
 * Singleton domain object that contains all updates as retrieved from resource file. It is the
 * class that provides all information to kafka producers.
 */
@Slf4j
public class BusLocationUpdates {

  private static BusLocationUpdates classInstance;
  private final LinkedList<BusLocation> busLocations;

  /**
   * If there is no instance f this class created yet, create one, else return the existing
   * instance.
   *
   * @param filename the name of the file to generate bus location in case the instance is to be
   *     created.
   * @return the existing or newly created BusLocationUpdates object.
   */
  public static BusLocationUpdates getInstance(String filename) {
    if (classInstance == null) {
      classInstance = new BusLocationUpdates(filename);
    }
    return classInstance;
  }

  private BusLocationUpdates(String filename) {
    this.busLocations = generateBusLocations(filename);
  }

  /**
   * Reads bus location updates from resource file and creates a list with bus locations to provide.
   *
   * @param filename the name of the file to get bus location information from.
   * @return a linked list of bus locations as read from file.
   */
  private LinkedList<BusLocation> generateBusLocations(String filename) {
    var busLocationsList = new LinkedList<BusLocation>();
    try {

      // Load file and create scanner to read it.
      var busLocationsFile = ResourceUtils.getFile(filename);
      var scanner = new Scanner(busLocationsFile);

      // Create a BusLocation object from each line read.
      while (scanner.hasNextLine()) {
        var line = scanner.nextLine();
        var busLocation = new BusLocation(line);
        busLocationsList.add(busLocation);
      }
      scanner.close();

      // TODO: Handle this exception.
    } catch (FileNotFoundException exception) {
      log.error(exception.getMessage());
    }
    return busLocationsList;
  }

  /**
   * Remove and return the next update available in bus locations list. This update is always in the
   * first container of the linked list the first.
   *
   * @return the removed bus location object.
   */
  public BusLocation getNextUpdate() {
    return busLocations.removeFirst();
  }

  /** Return true if there are more updates left, false otherwise. */
  public boolean hasUpdates() {
    return !this.busLocations.isEmpty();
  }
}
