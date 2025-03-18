package io.tidy.bigsecretary.briefcase.domain;

import io.tidy.bigsecretary.briefcase.dto.BriefcaseCreate;
import io.tidy.bigsecretary.briefcase.dto.BriefcaseCreateResult;
import io.tidy.bigsecretary.briefcase.dto.BriefcaseUpdate;
import io.tidy.bigsecretary.briefcase.dto.BriefcaseUpdateResult;
import io.tidy.bigsecretary.briefcase.exception.BriefcaseErrorCode;
import io.tidy.bigsecretary.briefcase.repository.BriefcaseRepository;
import io.tidy.bigsecretary.common.exception.CommonException;
import io.tidy.bigsecretary.user.domain.AuthenticatedUserContext;
import io.tidy.bigsecretary.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BriefcaseService {
  private final BriefcaseRepository briefcaseRepository;
  private final AuthenticatedUserContext authenticatedUserContext;

  @Transactional
  public BriefcaseCreateResult create(BriefcaseCreate create, Authentication authentication) {
    User user = authenticatedUserContext.findLoginUser(authentication);
    Briefcase result =
        briefcaseRepository.save(Briefcase.builder().name(create.getName()).user(user).build());
    return new BriefcaseCreateResult(result.getId(), result.getName());
  }

  @Transactional
  public BriefcaseUpdateResult update(Long id, BriefcaseUpdate update, Authentication authentication) {
    User user = authenticatedUserContext.findLoginUser(authentication);
    Briefcase briefcase = briefcaseRepository.findById(id).orElseThrow(() -> new CommonException(BriefcaseErrorCode.NOT_FOUND));
    if(!briefcase.getUser().equals(user)) {
      throw new CommonException(BriefcaseErrorCode.FORBIDDEN);
    }
    briefcase.update(update.getName());
    return new BriefcaseUpdateResult(briefcase.getId(), briefcase.getName());
  }
}
