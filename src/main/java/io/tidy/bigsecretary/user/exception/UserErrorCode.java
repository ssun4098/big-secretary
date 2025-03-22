package io.tidy.bigsecretary.user.exception;

import io.tidy.bigsecretary.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
  NOT_FOUND("U-404-1", "존재하지 않는 회원입니다.", HttpStatus.NOT_FOUND);
  private final String code;
  private final String message;
  private final HttpStatus status;

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public HttpStatus getStatus() {
    return this.status;
  }
}
