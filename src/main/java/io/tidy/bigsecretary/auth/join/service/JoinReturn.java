package io.tidy.bigsecretary.auth.join.service;

import lombok.Getter;

@Getter
public class JoinReturn {
  private Long id;

  public JoinReturn(Long id) {
    this.id = id;
  }
}
