package io.tidy.bigsecretary.auth.join.api;

import io.tidy.bigsecretary.auth.join.service.JoinReturn;
import io.tidy.bigsecretary.auth.join.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JoinApi {

  private final JoinService joinService;

  @PostMapping("/api/join")
  public ResponseEntity<JoinReturn> join(@RequestBody JoinReq joinReq) {
    return ResponseEntity.ok(joinService.join(joinReq.toDomain()));
  }
}
