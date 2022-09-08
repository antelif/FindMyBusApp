package com.antelif.findmybusapp.domain.exception;

import com.antelif.findmybusapp.domain.error.GenericError;

/** Invalid file exception to be thrown when resource file does not contain correct information. */
public class InvalidFileException extends GenericException {

  private final GenericError genericError;

  public InvalidFileException(GenericError genericError) {
    super(genericError);
    this.genericError = genericError;
  }
}
