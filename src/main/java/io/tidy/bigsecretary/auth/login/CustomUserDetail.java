package io.tidy.bigsecretary.auth.login;

import io.tidy.bigsecretary.user.domain.User;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {

  private final LoginUser user;

  private CustomUserDetail(User user) {
    this.user = new LoginUser(user.getId(), user.getPhone(), user.getPassword(), user.isLocked());
  }

  public Long getId() {
    return user.id();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return this.user.password();
  }

  @Override
  public String getUsername() {
    return this.user.phone();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !user.locked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return !user.locked();
  }

  @Override
  public String toString() {
    return "CustomUserDetail{" + "user=" + user.id() + '}';
  }

  public static CustomUserDetail fromUserEntity(User user) {
    return new CustomUserDetail(user);
  }

    private record LoginUser(Long id, String phone, String password, boolean locked) {
  }
}
