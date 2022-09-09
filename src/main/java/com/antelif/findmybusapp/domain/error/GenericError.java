package com.antelif.findmybusapp.domain.error;

import lombok.Getter;

/** Contains all error along with their codes and description. */
public enum GenericError {
  INVALID_FILE(1, "Could not read bus locations from file"),
  FILE_NOT_FOUND(2, "Could not find bus locations file");
  @Getter private final Integer errorCode;
  @Getter private final String errorMessage;

  /** Returns the name of the generic error. */
  public String getName() {
    return this.name();
  }

  GenericError(Integer errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
}
