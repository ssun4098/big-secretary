package io.tidy.bigsecretary.user.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhone(String phone);
    Optional<User> findByPhone(String phone);
}
