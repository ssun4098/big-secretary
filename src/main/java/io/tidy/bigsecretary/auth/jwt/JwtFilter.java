package io.tidy.bigsecretary.auth.jwt;

import io.tidy.bigsecretary.auth.login.CustomUserDetail;
import io.tidy.bigsecretary.user.domain.AuthenticatedUserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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
  private static final String BEARER = "Bearer ";
  private final AuthenticatedUserContext authenticatedUserContext;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (Objects.nonNull(authorization) && authorization.startsWith(BEARER)) {
      String id = jwtProvider.getUuidByToken(authorization.substring(BEARER.length()));
      if (Objects.nonNull(id)) {
        UserDetails customUserDetail =
            CustomUserDetail.of(authenticatedUserContext.findByUuid(id));
        SecurityContextHolder.getContext()
            .setAuthentication(
                new UsernamePasswordAuthenticationToken(
                    customUserDetail, null, customUserDetail.getAuthorities()));
      }
    }
    filterChain.doFilter(request, response);
  }
}
