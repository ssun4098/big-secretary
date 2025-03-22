package io.tidy.bigsecretary.briefcase.web;

import io.tidy.bigsecretary.auth.login.CustomUserDetail;
import io.tidy.bigsecretary.briefcase.domain.BriefcaseService;
import io.tidy.bigsecretary.briefcase.dto.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/briefcase")
@RestController
public class BriefcaseApi {
  private final BriefcaseService briefcaseService;

  @GetMapping
  public ResponseEntity<List<BriefcaseProfileResult>> getBriefcaseList(
      @AuthenticationPrincipal CustomUserDetail customUserDetail,
      @PageableDefault(size = 10) Pageable pageable) {
    return ResponseEntity.ok(briefcaseService.findBriefcaseList(pageable, customUserDetail));
  }

  @PostMapping
  public ResponseEntity<BriefcaseCreateResult> createBriefcase(
      @AuthenticationPrincipal CustomUserDetail customUserDetail,
      @RequestBody BriefcaseCreateReq create) {
    return ResponseEntity.status(201)
        .body(briefcaseService.create(create.toService(), customUserDetail));
  }

  @PutMapping("/{id}")
  public ResponseEntity<BriefcaseUpdateResult> updateBriefcase(
      @AuthenticationPrincipal CustomUserDetail customUserDetail,
      @PathVariable Long id,
      @RequestBody BriefcaseUpdateReq update) {
    return ResponseEntity.ok(briefcaseService.update(id, update.toService(), customUserDetail));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBriefcase(
      @AuthenticationPrincipal CustomUserDetail customUserDetail, @PathVariable Long id) {
    briefcaseService.delete(id, customUserDetail);
    return ResponseEntity.noContent().build();
  }
}
