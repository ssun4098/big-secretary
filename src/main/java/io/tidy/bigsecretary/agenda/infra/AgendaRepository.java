package io.tidy.bigsecretary.agenda.infra;

import io.tidy.bigsecretary.agenda.domain.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {}
