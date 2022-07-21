package com.antelif.findmybusapp.service;

import com.antelif.findmybusapp.configuration.AppProperties;
import com.antelif.findmybusapp.domain.BusLocation;
import com.antelif.findmybusapp.domain.BusLocationUpdates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * BusLocationUpdates service that works as middleware between the BusLocationsUpdates domain object
 * and the kafka producer.
 */
@Service
@RequiredArgsConstructor
public class BusLocationUpdatesService {
  private final AppProperties appProperties;

  /**
   * Remove and return the next update available in bus locations list. This update is always in the
   * first container of the linked list the first.
   *
   * @return the removed bus location object.
   */
  public BusLocation getNextUpdate() {
    return BusLocationUpdates.getInstance(appProperties.getBusLocationSourceFile()).getNextUpdate();
  }

  /** Return true if there are more updates left, false otherwise. */
  public boolean hasUpdates() {
    return BusLocationUpdates.getInstance(appProperties.getBusLocationSourceFile()).hasUpdates();
  }
}
