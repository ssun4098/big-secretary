package io.tidy.bigsecretary.auth.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tidy.bigsecretary.auth.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
  private final ObjectMapper objectMapper;

  @Value("${login.expired}")
  private Long expired;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
    SecurityContextHolder.getContext().setAuthentication(authentication);
    LoginSuccessToken responseToken =
            new LoginSuccessToken(
                    jwtProvider.createToken(
                            customUserDetail.getUuid(),
                            String.valueOf(customUserDetail.getAuthorities().stream().findFirst().orElse(null)),
                            expired));
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(responseToken));
  }

  @Getter
  @AllArgsConstructor
  private static class LoginSuccessToken {
    private String accessToken;
  }
}
