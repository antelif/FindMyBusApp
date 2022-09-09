package com.antelif.findmybusapp.service;

import com.antelif.findmybusapp.domain.error.ErrorResponse;
import com.antelif.findmybusapp.domain.exception.GenericException;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** Error handler service. */
@Service
@Slf4j
public class ErrorHandler {

  private ErrorHandler() {}

  /**
   * Logs the exception error message.
   *
   * @param exception the exception that occurred.
   */
  public static void printError(GenericException exception) {
    log.error(new ErrorResponse(exception.getGenericError(), Instant.now()).toString());
  }
}
