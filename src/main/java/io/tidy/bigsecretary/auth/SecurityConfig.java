package io.tidy.bigsecretary.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tidy.bigsecretary.auth.jwt.JwtFilter;
import io.tidy.bigsecretary.auth.login.CustomLoginFailHandler;
import io.tidy.bigsecretary.auth.login.CustomLoginSuccessHandler;
import io.tidy.bigsecretary.auth.login.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

  private final AuthenticationConfiguration configuration;
  private final ObjectMapper objectMapper;
  private final CustomLoginSuccessHandler successHandler;
  private final CustomLoginFailHandler failHandler;
  private final JwtFilter jwtFilter;

  @Bean
  public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
    return http
            .authorizeHttpRequests(
            (request) -> {
              request.requestMatchers("/api/login").permitAll();
            })
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(
            (session) -> {
              session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
        .addFilterAt(
            loginFilter(configuration, objectMapper, successHandler, failHandler),
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtFilter, LoginFilter.class)
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public LoginFilter loginFilter(
      AuthenticationConfiguration configuration,
      ObjectMapper objectMapper,
      AuthenticationSuccessHandler successHandler,
      AuthenticationFailureHandler failureHandler)
      throws Exception {
    return new LoginFilter(
        authenticationManager(configuration), objectMapper, successHandler, failureHandler);
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }
}
