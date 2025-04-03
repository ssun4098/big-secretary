package io.tidy.bigsecretary.user.infra;

import io.tidy.bigsecretary.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private User user;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final String name = "Kim";
    private final String phone = "01022920224";
    private final String password = "00000000";

    @BeforeEach
    public void init() {
        user = userRepository.save(User.builder()
                .name(name)
                .phone(phone)
                .password(passwordEncoder.encode(password))
                .build());
    }

    @Test
    void testSave() {
        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(phone, user.getPhone());
        assertTrue(passwordEncoder.matches(password, user.getPassword()));
        assertFalse(user.isDeleted());
        assertFalse(user.isLocked());
    }

    @Test
    void testFindByUuid() {
        Optional<User> result = userRepository.findByUuid(user.getUuid());
        assertTrue(result.isPresent());
        assertEquals(user.getUuid(), result.get().getUuid());
        assertEquals(user.getId(), result.get().getId());
    }
}
