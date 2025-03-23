package io.tidy.bigsecretary.user.infra;

import io.tidy.bigsecretary.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByPhone(String phone);

  Optional<User> findByPhone(String phone);

  Optional<User> findByUuid(String uuid);
}
