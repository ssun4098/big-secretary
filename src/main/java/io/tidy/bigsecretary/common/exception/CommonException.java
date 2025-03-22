package io.tidy.bigsecretary.common.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
  private final ErrorCode errorCode;

  public CommonException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
