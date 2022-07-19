package com.antelif.findmybusapp.domain;

import lombok.Getter;
import lombok.Setter;

/** Domain object that contains information about a bus route. */
@Getter
@Setter
public class Route extends Bus {
  private Long routeId;
  private Integer routeCode;
  private String routeName;
}
