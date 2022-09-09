package com.antelif.findmybusapp.domain;

import static com.antelif.findmybusapp.domain.constant.Common.PRODUCER;

import java.util.LinkedList;
import java.util.List;
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
   * If there is no instance of this class created yet, create one, else return the existing
   * instance.
   *
   * @param busLocations a list with bus locations to initialize updates list from.
   * @return the existing or newly created BusLocationUpdates object.
   */
  public static BusLocationUpdates getInstance(List<BusLocation> busLocations) {
    if (classInstance == null) {
      classInstance = new BusLocationUpdates(busLocations);
    }
    return classInstance;
  }

  private BusLocationUpdates(List<BusLocation> busLocations) {
    this.busLocations = new LinkedList<>(busLocations);
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
