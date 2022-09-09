package com.antelif.findmybusapp.domain.exception;

import com.antelif.findmybusapp.domain.error.GenericError;
import lombok.Getter;

/** Generic exception. */
@Getter
public class GenericException extends RuntimeException {

  private final GenericError genericError;

  public GenericException(GenericError genericError) {
    super(genericError.getErrorMessage());
    this.genericError = genericError;
  }
}
