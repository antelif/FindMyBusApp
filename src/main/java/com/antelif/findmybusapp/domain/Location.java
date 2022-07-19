package com.antelif.findmybusapp.domain;

import lombok.Getter;
import lombok.Setter;

/** Domain object that contains information about a location on the map. */
@Getter
@Setter
public class Location {
  private Long latitude;
  private Long altitude;
}
