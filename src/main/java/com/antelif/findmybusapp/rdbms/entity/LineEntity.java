package com.antelif.findmybusapp.rdbms.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** Entity class that contains information about a line object as saved in database. */
@Entity
@Table(name = "line")
@Getter
@Setter
public class LineEntity {

  @Id private int id;
  private int code;
  private String name;
}
