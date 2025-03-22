package io.tidy.bigsecretary.auth.join.service;

import lombok.Getter;

@Getter
public class JoinParam {
  private String phone;
  private String name;
  private String password;

  public JoinParam(String phone, String name, String password) {
    this.phone = phone;
    this.name = name;
    this.password = password;
  }
}
