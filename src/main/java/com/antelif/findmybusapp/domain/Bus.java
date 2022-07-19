package com.antelif.findmybusapp.domain;

import lombok.Getter;
import lombok.Setter;

/** Domain object that contains information about a bus. */
@Getter
@Setter
public class Bus {
  private Long busId;
  private Long lineNumber;
  private String lineName;
}
