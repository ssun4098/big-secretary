package io.tidy.bigsecretary.auth.jwt;

import io.tidy.bigsecretary.auth.login.CustomLoginSuccessHandler;
import io.tidy.bigsecretary.auth.login.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final CustomUserDetailService userDetailService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    Cookie cookie = findCookie(request.getCookies());
    if (Objects.nonNull(cookie)) {
      String id = jwtProvider.getUuidByToken(cookie.getValue());
      if (Objects.nonNull(id)) {
        UserDetails customUserDetail = userDetailService.loadUserById(Long.parseLong(id));
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                customUserDetail, null, customUserDetail.getAuthorities()));
      }
    }
    filterChain.doFilter(request, response);
  }

  private Cookie findCookie(Cookie[] cookies) {
    if(Objects.isNull(cookies)) {
      return null;
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(CustomLoginSuccessHandler.LOGIN_SUCCESS_COOKIE)) {
        return cookie;
      }
    }
    return null;
  }
}
