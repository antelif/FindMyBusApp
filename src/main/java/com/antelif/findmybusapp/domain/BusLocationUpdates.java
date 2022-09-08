package com.antelif.findmybusapp.domain;

import static com.antelif.findmybusapp.domain.constant.Common.PRODUCER;

import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;

/**
 * Singleton domain object that contains all updates as retrieved from resource file. It is the
 * class that provides all information to kafka producers.
 */
@Profile(PRODUCER)
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
  public static BusLocationUpdates getInstance(LinkedList<BusLocation> filename) {
    if (classInstance == null) {
      classInstance = new BusLocationUpdates(filename);
    }
    return classInstance;
  }

  private BusLocationUpdates(LinkedList<BusLocation> busLocations){
    this.busLocations=busLocations;
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
