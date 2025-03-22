package io.tidy.bigsecretary.briefcase.domain;

import io.tidy.bigsecretary.auth.login.CustomUserDetail;
import io.tidy.bigsecretary.briefcase.dto.*;
import io.tidy.bigsecretary.briefcase.exception.BriefcaseErrorCode;
import io.tidy.bigsecretary.briefcase.repository.BriefcaseRepository;
import io.tidy.bigsecretary.common.exception.CommonException;
import io.tidy.bigsecretary.user.domain.AuthenticatedUserContext;
import io.tidy.bigsecretary.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BriefcaseService {
  private final BriefcaseRepository briefcaseRepository;
  private final AuthenticatedUserContext authenticatedUserContext;

  @Transactional
  public BriefcaseCreateResult create(BriefcaseCreate create, CustomUserDetail customUserDetail) {
    User user = authenticatedUserContext.findLoginUser(customUserDetail);
    Briefcase result =
        briefcaseRepository.save(Briefcase.builder().name(create.name()).user(user).build());
    return new BriefcaseCreateResult(result.getId(), result.getName());
  }

  @Transactional
  public BriefcaseUpdateResult update(
      Long id, BriefcaseUpdate update, CustomUserDetail customUserDetail) {
    User user = authenticatedUserContext.findLoginUser(customUserDetail);
    Briefcase briefcase =
        briefcaseRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(BriefcaseErrorCode.NOT_FOUND));
    if (!briefcase.getUser().equals(user)) {
      throw new CommonException(BriefcaseErrorCode.FORBIDDEN);
    }
    briefcase.update(update.getName());
    return new BriefcaseUpdateResult(briefcase.getId(), briefcase.getName());
  }

  public List<BriefcaseProfileResult> findBriefcaseList(
      Pageable pageable, CustomUserDetail customUserDetail) {
    User user = authenticatedUserContext.findLoginUser(customUserDetail);
    return briefcaseRepository.findByUser(user, pageable).stream()
        .map((briefcase -> new BriefcaseProfileResult(briefcase.getId(), briefcase.getName())))
        .toList();
  }

  @Transactional
  public void delete(Long id, CustomUserDetail customUserDetail) {
    User user = authenticatedUserContext.findLoginUser(customUserDetail);
    Briefcase briefcase =
        briefcaseRepository
            .findById(id)
            .orElseThrow(() -> new CommonException(BriefcaseErrorCode.NOT_FOUND));
    if (!briefcase.getUser().equals(user)) {
      throw new CommonException(BriefcaseErrorCode.FORBIDDEN);
    }
    briefcase.delete();
  }
}
