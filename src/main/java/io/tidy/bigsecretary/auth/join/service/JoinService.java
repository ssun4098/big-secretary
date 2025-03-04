package io.tidy.bigsecretary.auth.join.service;

import io.tidy.bigsecretary.user.infra.User;
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
      return null;
    }
    User result = userRepository.save(
        User.builder()
            .name(joinParam.getName())
            .phone(joinParam.getPhone())
            .password(passwordEncoder.encode(joinParam.getPassword()))
            .build());
    return new JoinReturn(result.getId());
  }
}
