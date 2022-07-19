package com.antelif.findmybusapp.domain;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

/** Domain object that contains information about the location of a bus. */
@Getter
@Setter
public class BusLocation {
  private Long busId;
  private Long routeId;
  private Long vehicleId;
  private Location location;
  private Instant time;
}
