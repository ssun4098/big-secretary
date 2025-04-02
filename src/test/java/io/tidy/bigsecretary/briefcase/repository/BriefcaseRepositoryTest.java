package io.tidy.bigsecretary.briefcase.repository;

import static org.junit.jupiter.api.Assertions.*;

import io.tidy.bigsecretary.briefcase.domain.Briefcase;
import io.tidy.bigsecretary.user.domain.User;
import io.tidy.bigsecretary.user.infra.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class BriefcaseRepositoryTest {
    @Autowired
    private BriefcaseRepository briefcaseRepository;
    @Autowired
    private UserRepository userRepository;
    private User user;
    private final String name= "Briefcase";


    @BeforeEach
    public void init() {
        user = userRepository.save(new User("Kim", "01000000000", "00000000"));
    }

    @Test
    void testSave() {
        Briefcase briefcase = Briefcase.builder()
                .name(name)
                .user(user)
                .build();
        Briefcase result = briefcaseRepository.save(briefcase);
        assertEquals(name, result.getName());
        assertFalse(result.isDeleted());
        assertEquals(user, result.getUser());
    }

    @Test
    void testFindByUser() {
        Briefcase briefcase = Briefcase.builder()
                .name(name)
                .user(user)
                .build();
        briefcaseRepository.save(briefcase);
        List<Briefcase> briefcases = briefcaseRepository.findByUser(user, PageRequest.of(0, 10));
        assertFalse(briefcases.isEmpty());
    }

}
