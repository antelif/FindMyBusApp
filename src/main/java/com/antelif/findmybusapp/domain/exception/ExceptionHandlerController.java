package com.antelif.findmybusapp.domain.exception;

import com.antelif.findmybusapp.domain.error.ErrorResponse;
import com.antelif.findmybusapp.domain.error.GenericError;
import java.time.Instant;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Contains handlers for all exceptions. */
@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

  /**
   * Handles a Generic Exception.
   *
   * @param exception the exception that was thrown.
   * @return an error response with information about the exception that was thrown.
   */
  @ExceptionHandler({GenericException.class, InvalidFileException.class})
  public ResponseEntity<ErrorResponse> handleGenericException(GenericException exception) {
    var errorResponse = buildErrorResponse(exception.getGenericError(), Instant.now());
    log.error(errorResponse.toString());
    return ResponseEntity.of(Optional.of(errorResponse));
  }

  private ErrorResponse buildErrorResponse(GenericError genericError, Instant errorTime) {
    return new ErrorResponse(genericError, errorTime);
  }
}
