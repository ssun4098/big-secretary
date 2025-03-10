package io.tidy.bigsecretary.briefcase.domain;

import io.tidy.bigsecretary.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Briefcase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 512)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean deleted = false;

    @Builder
    public Briefcase(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
