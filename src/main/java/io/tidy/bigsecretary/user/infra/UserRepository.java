package io.tidy.bigsecretary.user.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhone(String phone);
}
