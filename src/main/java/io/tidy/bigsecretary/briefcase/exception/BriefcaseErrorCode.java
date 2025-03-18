package io.tidy.bigsecretary.briefcase.exception;

import io.tidy.bigsecretary.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BriefcaseErrorCode implements ErrorCode {
    NOT_FOUND("B-404-1", "존재하지 않는 캘린더 입니다.", HttpStatus.NOT_FOUND),
    FORBIDDEN("B-403-1", "해당 캘린더에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN);
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
