package io.tidy.bigsecretary.auth.login;

import io.tidy.bigsecretary.auth.jwt.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtProvider jwtProvider;
  public static final String LOGIN_SUCCESS_COOKIE = "BS_UR_TO";

  @Value("${login.expired}")
  private Long expired;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
    SecurityContextHolder.getContext().setAuthentication(authentication);
    response.addCookie(createCookie(customUserDetail.getId()));
  }

  private Cookie createCookie(Long id) {
    Cookie cookie = new Cookie(LOGIN_SUCCESS_COOKIE, jwtProvider.createToken(String.valueOf(id), expired));
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(Math.toIntExact(expired));
    return cookie;
  }
}
