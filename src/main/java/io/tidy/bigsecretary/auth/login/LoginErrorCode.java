package io.tidy.bigsecretary.auth.login;

import io.tidy.bigsecretary.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum LoginErrorCode implements ErrorCode {
    REQUIRED_LOGIN("L-401-1", "로그인이 필요한 서비스입니다.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;

    @Override
    public String getCode() {
        return "";
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public HttpStatus getStatus() {
        return null;
    }
}
