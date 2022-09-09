package com.antelif.findmybusapp.domain.exception;

import com.antelif.findmybusapp.domain.error.ErrorResponse;
import com.antelif.findmybusapp.domain.error.GenericError;
import java.time.Instant;
import lombok.Getter;

/** Generic exception. */
@Getter
public class GenericException extends RuntimeException {

  private final GenericError genericError;

  public GenericException(GenericError genericError) {
    super((new ErrorResponse(genericError, Instant.now())).toString());
    this.genericError = genericError;
  }
}
