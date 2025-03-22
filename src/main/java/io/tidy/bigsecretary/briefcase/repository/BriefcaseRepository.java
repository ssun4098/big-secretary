package io.tidy.bigsecretary.briefcase.repository;

import io.tidy.bigsecretary.briefcase.domain.Briefcase;
import io.tidy.bigsecretary.user.domain.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BriefcaseRepository extends JpaRepository<Briefcase, Long> {
  List<Briefcase> findByUser(User user, Pageable pageable);
}
