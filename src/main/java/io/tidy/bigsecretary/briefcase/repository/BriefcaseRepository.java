package io.tidy.bigsecretary.briefcase.repository;

import io.tidy.bigsecretary.briefcase.domain.Briefcase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BriefcaseRepository extends JpaRepository<Briefcase, Long> {}
