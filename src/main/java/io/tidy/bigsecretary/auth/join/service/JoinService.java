package io.tidy.bigsecretary.auth.join.service;

import io.tidy.bigsecretary.auth.join.exception.JoinErrorCode;
import io.tidy.bigsecretary.common.exception.CommonException;
import io.tidy.bigsecretary.user.domain.User;
import io.tidy.bigsecretary.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JoinService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public JoinReturn join(JoinParam joinParam) {
    if (userRepository.existsByPhone(joinParam.getPhone())) {
      throw new CommonException(JoinErrorCode.DUPLICATE_PHONE);
    }
    User result =
        userRepository.save(
            User.builder()
                .name(joinParam.getName())
                .phone(joinParam.getPhone())
                .password(passwordEncoder.encode(joinParam.getPassword()))
                .build());
    return new JoinReturn(result.getId());
  }
}
