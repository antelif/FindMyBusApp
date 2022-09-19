package com.antelif.findmybusapp.rdbms.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** Entity class that contains information about a route object as saved in database. */
@Entity
@Table(name = "route")
@Getter
@Setter
public class RouteEntity {

  @Id private int id;

  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "line_id", referencedColumnName = "id")
  private LineEntity line;

  private int code;
  private String name;
}
