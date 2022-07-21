package com.antelif.findmybusapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** Domain object that contains information about a location on the map. */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Location {
  private String latitude;
  private String altitude;
}
