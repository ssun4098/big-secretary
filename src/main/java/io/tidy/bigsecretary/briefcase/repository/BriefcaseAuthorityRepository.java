package io.tidy.bigsecretary.briefcase.repository;

import io.tidy.bigsecretary.briefcase.domain.Briefcase;
import io.tidy.bigsecretary.briefcase.domain.BriefcaseAuthority;
import io.tidy.bigsecretary.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BriefcaseAuthorityRepository extends JpaRepository<BriefcaseAuthority, Long> {
  Optional<BriefcaseAuthority> findByUserAndBriefcase(User user, Briefcase briefcase);
}
