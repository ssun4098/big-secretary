package io.tidy.bigsecretary.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
  String getCode();

  String getMessage();

  HttpStatus getStatus();
}
