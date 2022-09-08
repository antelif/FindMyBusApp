package com.antelif.findmybusapp.domain;

import static com.antelif.findmybusapp.domain.error.GenericError.INVALID_FILE;

import com.antelif.findmybusapp.domain.exception.InvalidFileException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Domain object that contains information about the location of a bus. */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BusLocation {
  private Long busId;
  private Long routeId;
  private Long vehicleId;
  private Location location;
  private String time;

  public BusLocation(String line) {
    var tokens = line.split(",");

    if (tokens.length != 6) {
      throw new InvalidFileException(INVALID_FILE);
    }
    this.busId = Long.valueOf(tokens[0]);
    this.routeId = Long.valueOf(tokens[1]);
    this.vehicleId = Long.valueOf(tokens[2]);
    this.location = new Location(tokens[3], tokens[4]);
    this.time = tokens[5];
  }
}
