package com.antelif.findmybusapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** Domain object that contains information about the location of a bus. */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BusLocation {
  private Long busId;
  private Long routeId;
  private Long vehicleId;
  private Location location;
  private String time;

  public BusLocation(String line) {
    var tokens = line.replace(" ", "").split(",");

    // TODO: Create and handle customized exception.
    if (tokens.length != 6) {
      throw new RuntimeException("Invalid line in txt file.");
    }
    this.busId = Long.valueOf(tokens[0]);
    this.routeId = Long.valueOf(tokens[1]);
    this.vehicleId = Long.valueOf(tokens[2]);
    this.location = new Location(tokens[3], tokens[4]);
    this.time = tokens[5];
  }
}
