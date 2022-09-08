package com.antelif.findmybusapp.service;

import static com.antelif.findmybusapp.domain.constant.Common.PRODUCER;

import com.antelif.findmybusapp.domain.BusLocation;
import com.antelif.findmybusapp.domain.BusLocationUpdates;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * BusLocationUpdates service that works as middleware between the BusLocationsUpdates domain object
 * and the kafka producer.
 */
@Profile(PRODUCER)
@Service
@RequiredArgsConstructor
public class BusLocationUpdatesService {
  private final ResourceFileLoader resourceFileLoader;

  /**
   * Remove and return the next update available in bus locations list. This update is always in the
   * first container of the linked list the first.
   *
   * @return the removed bus location object.
   */
  public BusLocation getNextUpdate() {

    return BusLocationUpdates.getInstance(resourceFileLoader.getBusLocations()).getNextUpdate();
  }

  /** Return true if there are more updates left, false otherwise. */
  public boolean hasUpdates() {

    if (resourceFileLoader.getBusLocations().isEmpty()) {
      resourceFileLoader.generateBusLocations();
    }
    return BusLocationUpdates.getInstance(resourceFileLoader.getBusLocations()).hasUpdates();
  }
}
