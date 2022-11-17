package ru.practicum.ewmmain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmain.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
