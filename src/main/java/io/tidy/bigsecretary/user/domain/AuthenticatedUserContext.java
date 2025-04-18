package io.tidy.bigsecretary.user.domain;

import io.tidy.bigsecretary.auth.login.CustomUserDetail;
import io.tidy.bigsecretary.common.exception.CommonException;
import io.tidy.bigsecretary.user.exception.UserErrorCode;
import io.tidy.bigsecretary.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticatedUserContext {
  private final UserRepository userRepository;

  public User findLoginUser(CustomUserDetail customUserDetail) {
    return userRepository
        .findById(customUserDetail.getId())
        .orElseThrow(() -> new CommonException(UserErrorCode.NOT_FOUND));
  }

  public User findByUuid(String id) {
    return userRepository
        .findByUuid(id)
        .orElseThrow(() -> new CommonException(UserErrorCode.NOT_FOUND));
  }
}
