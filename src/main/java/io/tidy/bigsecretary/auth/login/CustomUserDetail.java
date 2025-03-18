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
    this.user = new LoginUser(user.getId(), user.getPhone(), user.getPassword());
  }

  public Long getId() {
    return user.getId();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public String getUsername() {
    return this.user.getPhone();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String toString() {
    return "CustomUserDetail{" +
            "user=" + user.getId() +
            '}';
  }

  public static CustomUserDetail fromUserEntity(User user) {
    return new CustomUserDetail(user);
  }

  @Getter
  private static class LoginUser {
    private Long id;
    private String phone;
    private String password;

    public LoginUser(Long id, String phone, String password) {
      this.id = id;
      this.phone = phone;
      this.password = password;
    }
  }
}
