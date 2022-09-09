package com.antelif.findmybusapp.domain.error;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** Contains information about the error response when an exception occurs. */
@Getter
@Setter
@ToString
public class ErrorResponse {

  private String errorName;
  private Integer errorCode;
  private String errorMessage;
  private Instant errorTime;

  /**
   * Constructor that create an error response from a generic error and the time the error occured.
   *
   * @param genericError the error that occurred,
   * @param errorTime the time the error occurred.
   */
  public ErrorResponse(GenericError genericError, Instant errorTime) {
    this.errorName = genericError.getName();
    this.errorCode = genericError.getErrorCode();
    this.errorMessage = genericError.getErrorMessage();
    this.errorTime = errorTime;
  }
}
