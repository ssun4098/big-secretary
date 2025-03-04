package io.tidy.bigsecretary.auth.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

  public static final String PATH = "/api/login";
  public final ObjectMapper objectMapper;

  public LoginFilter(
      AuthenticationManager authenticationManager,
      ObjectMapper objectMapper,
      AuthenticationSuccessHandler successHandler,
      AuthenticationFailureHandler failureHandler) {
    super(PATH, authenticationManager);
    this.objectMapper = objectMapper;
    super.setAuthenticationSuccessHandler(successHandler);
    super.setAuthenticationFailureHandler(failureHandler);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException {
    LoginForm loginForm = objectMapper.readValue(request.getInputStream(), LoginForm.class);

    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(
            loginForm.getPhone(), loginForm.getPassword(), null);

    return this.getAuthenticationManager().authenticate(authToken);
  }

  @Getter
  private static class LoginForm {
    private String phone;
    private String password;
  }
}
