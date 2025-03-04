package io.tidy.bigsecretary.auth.join.api;

import io.tidy.bigsecretary.auth.join.service.JoinParam;
import lombok.Getter;

@Getter
public class JoinReq {
  private String phone;
  private String name;
  private String password;

  public JoinParam toDomain() {
    return new JoinParam(phone, name, password);
  }
}
