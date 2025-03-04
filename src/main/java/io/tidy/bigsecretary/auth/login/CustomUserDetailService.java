package io.tidy.bigsecretary.auth.login;

import io.tidy.bigsecretary.user.infra.User;
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
    User user = userRepository.findByPhone(username).orElseThrow(() -> new RuntimeException());
    return new CustomUserDetail(user);
  }

  public UserDetails loadUserById(Long id) {
    User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException());
    return new CustomUserDetail(user);
  }
}
