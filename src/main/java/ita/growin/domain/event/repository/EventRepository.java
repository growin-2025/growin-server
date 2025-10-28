package ita.growin.domain.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ita.growin.domain.event.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
