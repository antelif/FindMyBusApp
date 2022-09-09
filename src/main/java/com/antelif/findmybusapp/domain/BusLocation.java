package com.antelif.findmybusapp.domain;

import static com.antelif.findmybusapp.domain.error.GenericError.INVALID_FILE;

import com.antelif.findmybusapp.domain.exception.InvalidFileException;
import com.antelif.findmybusapp.service.ErrorHandler;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/** Domain object that contains information about the location of a bus. */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Slf4j
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
      var customException = new InvalidFileException(INVALID_FILE);
      ErrorHandler.printError(customException);
      throw customException;
    }
    this.busId = Long.valueOf(tokens[0].replace(" ", ""));
    this.routeId = Long.valueOf(tokens[1].replace(" ", ""));
    this.vehicleId = Long.valueOf(tokens[2].replace(" ", ""));
    this.location = new Location(tokens[3], tokens[4].replace(" ", ""));
    this.time = tokens[5];
  }
}
