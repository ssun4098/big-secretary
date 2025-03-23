package io.tidy.bigsecretary.auth.login;

import io.tidy.bigsecretary.common.exception.CommonException;
import io.tidy.bigsecretary.user.domain.User;
import io.tidy.bigsecretary.user.exception.UserErrorCode;
import io.tidy.bigsecretary.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByPhone(username)
            .orElseThrow(() -> new CommonException(UserErrorCode.NOT_FOUND));
    return CustomUserDetail.of(user);
  }
}
