package io.tidy.bigsecretary.agenda.domain;

import io.tidy.bigsecretary.briefcase.domain.Briefcase;
import io.tidy.bigsecretary.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 512, nullable = false)
    private String title;

    @Lob
    @Column(name = "explanation")
    private String explanation;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "agenda_type", length = 16)
    private AgendaType agendaType;

    @Column(name = "alert_time")
    private LocalDateTime alertTime;

    @ManyToOne
    @Column(name = "briefcase_id", nullable = false)
    private Briefcase briefcase;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
